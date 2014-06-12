N-Tuple Networks for the Game 2048 
==============================

This code allows to produce a performance profile for a weighted piece counter
(WPC) strategy for Othello.

For more information refer to:

Jaśkowski, W.; Liskowski, P.; Szubert, M. & Krawiec, K. Blum, C. (Ed.) 
Improving Coevolution by Random Sampling GECCO'13: Proceedings of the 15th 
annual conference on Genetic and Evolutionary Computation, ACM, 2013 
www.cs.put.poznan.pl/wjaskowski/


Authors
-------
Wojciech Jaśkowski <wjaskowski@cs.put.poznan.pl>
Paweł Liskowski <pliskowski@cs.put.poznan.pl>


Building
--------
You need Java 1.7 and Maven. To build execute:

mvn package


Running
-------
cd cevo-experiments
wget http://www.cs.put.poznan.pl/wjaskowski/pub/gecco2013/othello-pprofile-db-random-new.dump
java -Dlog4j.configuration=file:log4j.properties -jar ../cevo-dist/target/cevo-dist-1.0-single.jar wpc1.txt

where wpc1.txt is a file with WPC strategy representation


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
