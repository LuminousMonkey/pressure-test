# File Integrity Database #

### Overview

A client is building a system that will test file system integrity. To
do this is will compute cryptographic hashes of all files present
under a filesystem path.

You have been tasked with writing a command line tool, that is passed
a file system path to analyse. The programme should analyse all the
files in this directory or any subdirectory of this directory.

As it traverses the filesystem, for each file it finds it needs to
read that file and calculate the md5 and sha1 hash digest of the files
contents. It should return this in a data structure that mirrors the
filesystem. The output of the programme should be a json encoded
string of this data.

The programme should be run from the command line and passed a single
argument of the folder to examine.

The programme should be efficient. Don't read files more than
necessary.

The programme should scale. Extremely large files should be hashed
without exhausting memory.

### Example

Given a directory 'test' with two files in it 'a.txt' and 'b.txt', the
programme should return a data structure like:

```
{"a.txt": {"sha1": "b725b68c6b302c5842f8d0a8bcfe03e74b708b6d",
	   "md5": "665528c0a017f7bbc89526cfa4541f12"},
 "b.txt": {"sha1": "74e5b2c1cd103fc4c9779e85404d23be09006631",
 	   "md5": "1e9cbac588d3baba3deea703fc1ab11c"}}
```

If directories are nested the data sctructure needs to mirror
this. For example, a directory that contained two subdirectories,
'one' and 'two', which each contained two files, might produce a
result like this:

```
{"one":
   {"a.txt": {"sha1": "b725b68c6b302c5842f8d0a8bcfe03e74b708b6d",
              "md5": "665528c0a017f7bbc89526cfa4541f12"},
    "b.txt": {"sha1": "74e5b2c1cd103fc4c9779e85404d23be09006631",
              "md5": "1e9cbac588d3baba3deea703fc1ab11c"}},
 "two":
   {"c.txt": {"sha1": "d6112c3e335ad92dddef6c300d9bd9c6e882c290",
              "md5": "6908674d5c6b60a3f7df99cfa8794982"},
    "d.txt": {"sha1": "0ecf7df743977eb10a6ddd4153a3f9a6a65d41f3",
              "md5": "ff32b7986e2b24608a87b519fb8e8b71"}}}
```

The programme could be run like:

$ ./calc path/to/process

### Tip

You can pretty print json data on stdout by piping to `python -mjson.tool`

### Test Data

There are three directories to test your programme with, `easy`,
`medium`, and `hard`. Here is working code run against each for your
verification:

#### Easy

```
$ ./calc.rb easy | python -mjson.tool
{
    "kitten.jpg": {
        "md5": "498a36bc1c919afd5d773751f9b6519c",
        "sha1": "5d26a1f5ad1fdc0f684dbaaf8fecdb35428c6e40"
    },
    "test.txt": {
        "md5": "7df49c98207b8613b0ea371982b2a3bc",
        "sha1": "aeec614878c5c8ffb43b559f47d2f08106d777d7"
    }
}
```

#### Medium

```
$ ./calc.rb medium | python -mjson.tool
{
    "random.dat": {
        "md5": "7776e4af02fb670a25eed7217e61519c",
        "sha1": "350056276c6cb6f518c96a539841dde46fd6b5fb"
    },
    "subdir": {
        "kitten.jpg": {
            "md5": "498a36bc1c919afd5d773751f9b6519c",
            "sha1": "5d26a1f5ad1fdc0f684dbaaf8fecdb35428c6e40"
        },
        "puppy.jpg": {
            "md5": "b39c9f4bc1cd2614e7f43240a6cd0fab",
            "sha1": "9a70af06832db30d7922a0616c61ff48576f9b94"
        },
        "subsubdir": {
            "piglet.jpg": {
                "md5": "4c4d8a60257a643d0bcd2cd33656b112",
                "sha1": "53620f90a9679a7c375447d9657589614b61c7bd"
            }
        }
    },
    "subdir2": {
        "sub": {
            "sub": {
                "empty": {},
                "sub": {
                    "sub": {
                        "duck.jpeg": {
                            "md5": "557cc5c2e01ae8af2a356d6e95d16ffb",
                            "sha1": "a9060f54e409a038f18de1b7f82a64e151caa579"
                        }
                    }
                }
            }
        }
    },
    "testfile.txt": {
        "md5": "bc0b7f873dd119b7ba045d9b1e048284",
        "sha1": "b23c974749c8625ab28c09c57b0c9933a0014a04"
    }
}
```

#### Hard

```
$ ./calc.rb hard | python -m json.tool
{
    "sub": {
        "sub": {
            "sub": {
                "sub": {
                    "sub": {
                        "sub": {
                            "sub": {}
                        }
                    }
                }
            },
            "zero.dat": {
                "md5": "cd573cfaace07e7949bc0c46028904ff",
                "sha1": "2a492f15396a6768bcbca016993f4b4c8b0b5307"
            }
        }
    }
}
```
