KNIFE
=====
Simple source code analysis for dependency resolution. This is a bit of a foray into the world of
Java compiler tools for me. It's also the first time I've really used Guice, so sorry if it's a
bit messy.

KNIFE uses data driven testing with JUnit4 and Yaml-specced files to easily create system tests.

On the agenda right now is: 
 * Own file globber, so that KNIFE can accept wildcards so it doesnt have to rely on files xarg'd from find
 * Better usage information (what params for what commands, eg KNIFE --help)
 * Maven POM-file parsing to allow for cross-module analysis
 