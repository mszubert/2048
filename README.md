N-Tuple Networks for the Game 2048 
==============================

This code allows to use n-tuple networks as an evaluation function for the game 2048.
For more information please visit <http://www.cs.put.poznan.pl/mszubert/projects/2048.html>

Authors
-------
* Marcin Szubert (<http://www.cs.put.poznan.pl/mszubert>)
* Wojciech Jaśkowski (<http://www.cs.put.poznan.pl/wjaskowski>)
* Paweł Liskowski (<http://www.cs.put.poznan.pl/pliskowski>)


Building
--------
You need Java 1.7 and Maven. To build execute:

mvn package


Running
-------

java -jar target/2048-1.0-jar-with-dependencies.jar players/best_standard_network.bin 100


Citing
------
Temporal Difference Learning of N-Tuple Networks for the Game 2048, Marcin Szubert and Wojciech Jaśkowski, Proceedings of IEEE Conference on Computational Intelligence and Games, August 2014, Dortmund, Germany [preprint](http://http://www.cs.put.poznan.pl/mszubert/pub/szubert2014cig.pdf "preprint")

License
-------
Licensed under the Apache License, Version 2.0 (the "License");
you may not use code in this repository except in compliance with 
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
