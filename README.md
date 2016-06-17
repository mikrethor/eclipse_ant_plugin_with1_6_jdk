Sometimes the compagny you are working for still use and force an older version of the JDK to compile some applications.
In my case I have to compile the sources using 1.6 JDK. It prevents me from using Eclipse Neon.
So to use Eclipse Neon I had to modify the ANT plugin to use it with 1.6 JDK.

This repository contains the ant eclipse plugins sources I modified.

ANT
===
Fisrtly, you need to compile the sources of this repository with Eclipse RCP.
The ANT sources I used were taken from the tag : [I20160525-2000](https://git.eclipse.org/c/platform/eclipse.platform.git/tag/?h=I20160525-2000).
Then I downgraded the code en 1.6. Mostly replacement of <> and some try-with-resources.

To use this Ant plugin with Eclipse. You need to copy them in the Eclipse plugins folder.

Be careful to delete the ant plugins already in the folder. You may have to change these sources to match the versions of the jars you are replacing.

I am currently using the jars from these sources in Eclipse Neon RC.

Have fun