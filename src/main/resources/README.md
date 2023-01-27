# File Naming Scheme

## Names

File names are always written using lower-case alphanumeric characters. These names can generally be composed of
individual fields which are logically separated by exactly one underscore. If only one field is used, then no such
separator must be present. In addition, no other leading or trailing characters are allowed.

Note that the field structure of certain files is standardized. For instance. all serialized graphs should be named
according to the following structure:

```
<country_code>_graph_<number_of_vertices>_<number_of_edges>_<version_number>
```

The country code used must be the one defined in the [ISO 3166-1](https://en.wikipedia.org/wiki/ISO_3166-1) standard. In
case the version number of the graph is not available, its size of the graph is used to implicitly signify it.

In addition, ALT data must be named as such:

```
<country_code>_alt_<optimization_criterion>_<number_of_landmarks>
```

The currently available optimization criteria are those of the minimum ```dist```-ance and travel ```time```.

## Extensions

File extensions are always writen using lower-case characters. Each file must have one and only one extension.

Note that, as is the case with names, certain extensions are standardised. For instance, all binary files, such as
serialized graphs and ALT data, should have a ```bin``` extension.

# File Organization

Files are always organized into individual directories based on the country they refer to, using the following
structure:

```
├── <country_code>
│   ├── alt
│   │   ├── *.bin
│   ├── *
│   │   ├── **/*.*
│   ├── *.bin
```

Note that graphs and ALT data must always be placed in the root of the corresponding and an appropriately named
directory, respectively.