#  Copyright 2021-2022 Dimitris Mantas
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

class SourceGraphReader:
    __FILE_DELIMITER__ = " "
    __FILE__ENCODING__ = "utf8"
    num_V = 0
    num_E = 0
    __V__ = None
    __E__ = None

    def __init__(self, filename: str) -> None:
        self.__filename__ = filename

    def split(self, V_filename: str, E_filename: str) -> None:
        self.__V__ = []
        self.__E__ = []
        with open(self.__filename__, encoding=self.__FILE__ENCODING__) as f:
            for line in f:
                if line.startswith("#"):
                    continue
                contents = line.strip().split(self.__FILE_DELIMITER__)
                if len(contents) == 3:
                    self.__V__.append(contents)
                elif len(contents) >= 6:
                    self.__E__.append(contents)
                elif len(contents) == 2:
                    self.num_V = int(contents[0])
                    self.num_E = int(contents[1])
                else:
                    continue
        if len(self.__V__) == self.num_V and len(self.__E__) == self.num_E:
            self.__write__(self.__V__, V_filename)
            self.__write__(self.__E__, E_filename)
        else:
            # TODO - Think of a better error message.
            raise RuntimeError("Dimension mismatch")

    def __write__(self, lines: list[list[str]], filename: str) -> None:
        with open(filename, "w", encoding=self.__FILE__ENCODING__) as f:
            for _, line in enumerate(lines):
                f.write(self.__FILE_DELIMITER__.join(line) + "\n")


def main():
    reader = SourceGraphReader("../source/grc_graph_8500875_8809699_source.txt")
    reader.split(f"../source/grc_graph_vertices_{reader.num_V}_source.txt",
                 f"../source/grc_graph_edges_{reader.num_E}_source.txt")


if __name__ == '__main__':
    main()
