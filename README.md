# README.md

## Introduction

This Git repository was created for **learning purposes** focused on **reverse engineering**, **open source**, and providing **simple code for beginners**. The main goal is to allow users to unpack `.exe` files that were originally `.jar` files.

## How to Identify if an EXE File was a JAR File

### Method 1: Using the DIE Program

- **Tool**: [Detect It Easy (DIE)](https://github.com/horsicq/Detect-It-Easy)
- **Instructions**: Follow the guide provided in the repository to analyze the executable file.

### Method 2: Using Hexed.it

- **Website**: [hexed.it](https://hexed.it)
- **Steps**:
  - Upload your `.exe` file to the site.
  - Look for byte patterns similar to `.class`.
  - If you find Java strings in the bytes, there is a possibility that this `.exe` is a packaged `.jar` file.
  - Search for keywords like `DefineClass` and `Java`. If found, it indicates a high probability that the file is packaged.
  ![image](https://github.com/user-attachments/assets/c7c013a0-b10e-43e0-adbd-c655c177ac2a) ![image](https://github.com/user-attachments/assets/386f3fc5-cd89-469d-a235-c01cf01d377e)


## Unpacking Method

### Overview

The unpacking process involves **JVM manipulation**. This method utilizes a Java Agent to create a dump `.jar` file on disk `C`, named `dumped.jar`, which will contain the unpacked classes.

### Steps to Unpack

1. **Download**: Get `dumper.jar` from the releases section.
  
2. **Open Command Prompt**:
   - Press `Win + R`, type `cmd`, and hit `Enter`.

3. **Set Java Tool Options**:
   - Create a variable for Java by executing the following command:
   ```cmd
   set JAVA_TOOL_OPTIONS=-javaagent:path_to_dumper_here.jar
4. ***Start your .exe file***:
   - And after a while we see that everything is ready and all classes have been dumped into: `C:\dumped.jar`
## Note
My methods may not work on some packers, as well as in addition to the packer may be obfuscated jar file, please take this into account.

**Please don't hit hard for this git, it's my first public work.**
