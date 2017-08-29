# Perceptron

## Compiling Java files using Eclipse IDE

1. Download this repository as ZIP
2. Create new `Java Project` in `Eclipse`
3. Right click on your `Java Project` --> `Import`
4. Choose `General` --> `Archive File`
5. Put directory where you downloaded ZIP in `From archive file`
6. Put `ProjectName/src` in `Into folder`
7. Click `Finish`

## Running the program

1. Right click on your `Java Project` --> `Run As` --> `Java Application`
2. Program will ask for directory of where the text files are
3. Choose `data` directory
4. Program will look for `image.data` inside that chosen folder

## Notes

- The algorithm for learning the weights is available <a href='https://github.com/rjperez94/Perceptron/blob/master/learning-weights-algo.gif'>here</a>

- The basic algorithm for the perceptron is <a href='https://github.com/rjperez94/Perceptron/blob/master/perceptron-algo.gif'>here</a>

## About the Data File

The file `image.data` consists of 100 image files, concatenated together. The image files are in PBM format, with exactly one comment line

- The first line contains `P1`;
- The second line contains a comment (starting with `#`) that contains the class of the image (`Yes` or `Other`)
- The third line contains the `width` and `height` (number of columns and rows)
- The remaining lines contain `1’s` and `0’s` representing the pixels in the image, with `1` representing black, and `0` representing white. The line breaks are ignored

### About PBM Files

Note that the PBM image format rules are a little more flexible than this

- The image files in the data set do not have any comments other than the one on the second line but the PBM rules allow comments starting with # at any point in the file after the first token
- In the image files in the data set, the pixels have no whitespace between them (other than the line breaks), but PBM rules also allow the 1’s and 0’s to be separated by white space
- The PBM rules do not allow image files to be concatenated into a single file
