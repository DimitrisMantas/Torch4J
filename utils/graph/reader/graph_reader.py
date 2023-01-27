#  Copyright 2021-2023 Dimitris Mantas
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
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
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.


class RawGraphReader:

    def __init__(self, filename: str, comments: str = "#", delimitr: str = " ", encoding: str = "utf8") -> None:
        self.__filename = filename
        self.__comments = comments
        self.__delimitr = delimitr
        self.__encoding = encoding

        self.__v = None
        self.__e = None

        self.num_e = None
        self.num_v = None

    def split(self, v_filename: str, e_filename: str) -> None:
        self.__v = []
        self.__e = []
        with open(self.__filename, encoding=self.__encoding) as f:
            for line in f:
                if line.startswith(self.__comments):
                    continue

                contents = line.strip().split(self.__delimitr)
                if len(contents) == 2:
                    self.num_v = int(contents[0])
                    self.num_e = int(contents[1])
                elif len(contents) == 3:
                    self.__v.append(contents)
                elif len(contents) >= 6:
                    self.__e.append(contents)
                else:
                    continue

        if len(self.__v) == self.num_v and len(self.__e) == self.num_e:
            self.__write(self.__v, v_filename)
            self.__write(self.__e, e_filename)
        elif len(self.__v) != self.num_v:
            raise RuntimeError(f"Failed to read {self.num_v} vertices. Got {len(self.__v)} instead.")
        else:
            raise RuntimeError(f"Failed to read {self.num_e} vertices. Got {len(self.__e)} instead.")

    def __write(self, lines: list[list[str]], filename: str) -> None:
        with open(filename, "w", encoding=self.__encoding) as f:
            for _, line in enumerate(lines):
                f.write(self.__delimitr.join(line) + "\n")


def main():
    reader = RawGraphReader("../source/8500875.raw.g.txt")
    reader.split(f"../source/{reader.num_v}.raw.v.txt", f"../source/8500875.raw.e.txt")


if __name__ == '__main__':
    main()
