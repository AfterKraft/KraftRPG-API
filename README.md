KraftRPG-API
============

An RPG plugin API for [SpongeAPI].

[Website] **|** [Javadocs] **|** [Dev Builds] **|** [KraftRPG]

Compilation
-----------

We use [Gradle](http://www.gradle.org) to handle our dependencies and
compatibility modules.<br>
* Check out this repo<br>
* Run `./gradlew` for Linux/Mac and `gradlew` for Windows

Importing to Eclipse
--------------------

Unlike KraftRPG (the plugin), KraftRPG-API easily imports into Eclipse just
fine with the gradle plugin.

Importing to IntelliJ
---------------------

IntelliJ imports the project as a Gradle project just fine.

Using KraftRPG-API
------------------

To use KraftRPG, it is advisable to depend on **KraftRPG-API** and **[SpongeAPI]**. This way it is
possible to have skills and effects to be version agnostic.

There is a maven repository available for all of KraftRPG and AfterKraft's projects: 
[http://nexus.afterkraft.com/content/groups/public/] <br>
To depend on [SpongeAPI], it is advisable to use their repository:
[http://repo.spongepowered.org/maven/]

[SpongeAPI]:https://github.com/SpongePowered/SpongeAPI
[Website]:https://afterkraft.com/forum/threads/an-introduction-to-kraftrpg.15/
[Javadocs]:http://ci.afterkraft.com/job/KraftRPG-API/javadoc/
[Dev Builds]:http://ci.afterkraft.com/job/KraftRPG-API/
[KraftRPG]:http://git.afterkraft.com/afterkraft/kraftrpg/tree/master
[http://nexus.afterkraft.com/content/groups/public/]: http://nexus.afterkraft.com/content/groups/public/
[http://repo.spongepowered.org/maven/]: http://repo.spongepowered.org/maven/
