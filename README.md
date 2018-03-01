# Graph-Compression (Shrink)

Shrink: Distance preserving graph compression

The ever increasing size of graphs makes them difficult to query and store. In this paper, we present Shrink, a compression method that reduces the size of the graph while preserving the distances between the nodes. The compression is based on the iterative merging of the nodes. During each merging, a system of linear equations is solved to define new edge weights in a way that the new weights have the least effect on the distances. Merging nodes continues until the desired size for the compressed graph is reached. The compressed graph, also known as the coarse graph, can be queried without decompression. As the complexity of distance-based queries such as shortest path queries is highly dependent on the size of the graph, Shrink improves the performance in terms of time and storage. Shrink not only provides the length of the shortest path but also identifies the nodes on the path. The approach has been applied to both weighted and unweighted graphs including road network, friendship network, collaboration network, web graph and social network. In the experiment, a road network with more than 2.5 million nodes is reduced to fifth while the average relative error is less than 1%.

This repository contains resources developed within the following paper:

Sadri, Amin, Flora D. Salim, Yongli Ren, Masoomeh Zameni, Jeffrey Chan, and Timos Sellis. "Shrink: Distance preserving graph compression." Information Systems 69 (2017): 180-193.
  
You can find the [paper](https://github.com/cruiseresearchgroup/Graph-Compression-Shrink-/blob/master/paper/1-s2.0-S0306437916306573-main.pdf) and [presentation](https://github.com/cruiseresearchgroup/Graph-Compression-Shrink-/blob/master/Presentation/ShrinkPresentation.pptx) in this repository. 

Alternative link: https://www.sciencedirect.com/science/article/pii/S0306437916306573

## Contents of the repository
This repository contains resources used and described in the paper.

The repository is structured as follows:
- `paper/`: Formal description of the algorithm and evaluation results
- `code/`: The Java project code
- `presentation/`: The presentation slides

## Code
The codes are available in `code/`. 
Input format: 
- Each row of the input file denotes an undirected edge. 
- The first two entries of each row denote the nodes of the edge and the third value is the edge weight. (e.g.  5 7 8.2 means there is an edge between node 5 and 7 and the weight is 8.2)
- If the graph is unweighted, comment line number 69 in Shortesstpath.java. In this case each, the program reads two values from each line. 
- The seprator between the numbers is space.
- The nodes must be labeled from 1 to the maximum number of nodes.
- The compression ratio is the (Original graph size)/(Coarse graph size).


The program gets the input file address (e.g. "H:\Datasets\graph.txt") and returns the output file in the same folder. The output file has ".out" extention (e.g. "graph.txt.out").


## Citation
If you use the resources presented in this repository, please cite (using the following BibTeX entry):
```
@article{sadri2017shrink,
  title={Shrink: Distance preserving graph compression},
  author={Sadri, Amin and Salim, Flora D and Ren, Yongli and Zameni, Masoomeh and Chan, Jeffrey and Sellis, Timos},
  journal={Information Systems},
  volume={69},
  pages={180--193},
  year={2017},
  publisher={Elsevier}
}
```
