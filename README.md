# photo-organizer
Based on the project created by
[Código Fonte TV](https://www.youtube.com/watch?v=L8KFB0VyEwo)

---
### How to run?

> Make a git clone on your git terminal.

> Open the cloned project in your IDE.

> Create a jar file and save it in a directory.

> Open your windows registry editor using regedit.

> Go to this directory:
> Computer \ HKEY_CLASSES_ROOT \ Directory \ Background \ shell.

> Select shell and create a key folder called photo_organizer.

> In the photo_organizer key create another called command.

> In command key insert this 
> "Your javaw.exe directory" -jar "Your jar file directory" "%V"

Example
> "c:\Program Files\Java\jdk-11.0.4\bin\javaw.exe" -jar "c:\Users\vinicius\Desktop\photoorganizer.jar" "%V"
