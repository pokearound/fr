# File rename
Multi-file rename using java.

[![Build Status](https://travis-ci.org/anandchakru/fr.svg?branch=master)](https://travis-ci.org/anandchakru/fr)
[![codecov](https://codecov.io/gh/anandchakru/fr/branch/master/graph/badge.svg)](https://codecov.io/gh/anandchakru/fr)


### Usage

```
usage: java -jar fr-1.0-jar-with-dependencies.jar.jar
 -a <arg>   Old file name, required, eg: ".jpg"
 -b <arg>   New file name, required, eg: "_M.jpg"
 -c         Auto-commit file renames, optional, defaults to false - only
            dry run
 -d <arg>   Directory to perform the renames, optional, defaults to
            current directory
 -e         Recursive, include sub directories, optional, defaults to
            false
 -f         Force-rename, if new file already exists it will be renamed as
            new_file_time, optional, defaults to false
 -v         Verbose output, optional, defaults to false

```

### Clone
```
git clone https://github.com/anandchakru/fr.git
cd fr
```

### Build
```
mvn clean package
```

### Execute
```
java -jar fr-*-dependencies.jar -a ".jpg" -b "_M.jpg" -c

```