import java.io.FileOutputStream
import java.io.IOException
import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation
import java.lang.instrument.IllegalClassFormatException
import java.security.ProtectionDomain
import java.util.HashMap
import java.util.logging.Level
import java.util.logging.Logger
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object Main {

    private lateinit var out: ZipOutputStream
    private val addedEntries: MutableMap<String, ZipEntry> = HashMap()
    private var count = 0

    @JvmStatic
    fun premain(agentArgs: String?, inst: Instrumentation) {
        out = ZipOutputStream(FileOutputStream("C:\\dumped.jar"))

        println("\n" +
                "░░░░░░░░░░░░░░░░▄▄████▄▄▄▄░░░░░░░░░░░\n" +
                "░░░░░░░░░░░▄▄▄██████████████▄▄░░░░░░░\n" +
                "░░░░░░░░░██████░░▀█████████████▄░░░░░\n" +
                "░░░░░░░▄██▄░▀░░░░▀▄▀████▀████████▄░░░\n" +
                "░░░░░░░██░░░░███░░░░░▀░░░░▀███████▄░░\n" +
                "░░░░░░░█░░░░░░▀░░░░░░▄▄▄▄░░░░██████░░\n" +
                "░░░░░░░█░░░░░░░░░░▄▀░░░░░▀▄░░▀█████░░\n" +
                "░░░░░▄█░░░░███▄▄░░█▄▀█████▄▄░░█████░░\n" +
                "░░░░░█░░▄▄▄░░░░█░░▀▀▀░░▀▄▄█▄▀▀▀▀███░░\n" +
                "░░░░░█░░░▄▀▀░▄█▀░░░▀█▄▄░▀▀█▄▀█▄░█▀█░░\n" +
                "░░░░░▀█░██▄▄░░▀▄░░▀▀█▄▄▀▀▀█▄▄█░░░▄▀░░\n" +
                "░░░░░░█░███▄▀▀▀▀▀█▀▀░█▄▄██▀▄▀░░░█░░░░\n" +
                "░░░░░░█░██████▀█████▀▀█░░▄█░░░░█░░░░░\n" +
                "░░▄░░░█░░█▀███░█░░█░░▄██▀░░▄░▄▀░░░░░░\n" +
                "█████░█▄▄█▀▀▀▀▀▀▀▀▀▀▀░▄░░▄▄▀█▄░░░░░░░\n" +
                "███████▀░░░░░░░░░░░░░░▄▀▀░░░███▄░░░░░\n" +
                "░▀▀▀▀░▀▄▄░░░░░▄▄▄▄▀▀▀░░░░█░▄██████▄░░\n" +
                "░░░░░░░░▄█▀▀▀▀░░░░░░░░█░▀░▄██████████\n" +
                "░░░░░▄▄███░░░▄░░░░░░▄▀░░░░██████████▀\n" +
        "Dumper by SenseTeam (Cra\$hdami) -> t.me/senseteam")

        Runtime.getRuntime().addShutdownHook(Thread {
            println("END STREAM")

            try {
                out.close()
            } catch (ex: IOException) {
                Logger.getLogger(Main::class.java.name).log(Level.SEVERE, null, ex)
            }
        })
        println("[!] Info of jvm: " + System.getProperty("java.vm.name") + " | " + System.getProperty("java.vm.version"))

        inst.addTransformer(Transformer())
    }

    private class Transformer : ClassFileTransformer {
        @Throws(IllegalClassFormatException::class)
        override fun transform(
            loader: ClassLoader?,
            className: String?,
            classBeingRedefined: Class<*>?,
            protectionDomain: ProtectionDomain?,
            classfileBuffer: ByteArray
        ): ByteArray? {
            if (className != null) {
                if (!className.startsWith("java") &&
                    !className.startsWith("sun") &&
                    !className.startsWith("javax") &&
                    !className.startsWith("com") &&
                    !className.startsWith("jdk") &&
                    !className.startsWith("org")
                ) {
                    println("[+] Dumping class: $className")
                    val newName = "$className.class".replace("/", ".")
                    try {
                        var name = "$className.class"
                        if (addedEntries.containsKey(name)) {
                            name = "$className${count++}.class"
                        }

                        val entry = ZipEntry(name)
                        addedEntries[name] = entry
                        out.putNextEntry(entry)
                        out.write(classfileBuffer, 0, classfileBuffer.size)
                        out.closeEntry()
                    } catch (ex: Throwable) {
                        println("[!] Exception while writing: $newName")
                        ex.printStackTrace()
                    }
                }
            }
            return classfileBuffer
        }
    }
}