# Fun With Images 3.0! Now with a GUI!
---

This program is able to apply common filters to your image as well as generate a few flags of some countries and some figures as well.
##  Navigating the UI:
---
Generating an image:
- You can generate an image using File > New and selecting the image you wish to generate.

Opening an image:
- You can open an image on your computer by using File > Open and selecting the desired image.

Saving an image:
- Once you have completed the necessary modification to the image you can save it using File > Save.

Applying filters:
- Please see the list a filters below for all the current supported filters. 
- To apply a filter, first open or generate an image and then use the filter menu to select a filter.

Undo / Redo:
- You can use edit > undo and edit > redo to undo and redo actions. The program allows up to 5 undo actions at this time.

##  Features!
---
- Filters that can be applied to the provided picture:
  - Sepia.
  - Greyscale.
  - Dithering.
  - Mosaic filter.
  - Blurring an image.
  - Sharpening an image.
- Flags that can be generated:
  - Flag of [Switzerland](https://en.wikipedia.org/wiki/Flag_of_Switzerland).
  - Flag of [Greece](https://en.wikipedia.org/wiki/Flag_of_Greece).
  - Flag of [France](https://en.wikipedia.org/wiki/Flag_of_France).
- Pictures that can be generated:
    - Horizontal/Vertical rainbow.
    - Classic chess board.

You can also:
  - Apply filters to the given image a few times.
  - Just load it once and apply filters as much as you want.

## Insturctions for Batch Scripts
 Commands associated with filtering an image. 
- load = path to the file needed to be modified.
- save = path to the file that will be created.
- blur 
- dithering
- greyscale
- mosaic
- sharpening
- sepia
  Examples: 
  load res/manhattan-small.png
  dithering
  save res/manhattan-small-ditherNew.png
  
  load res/manhattan-small.png
  sepia
  save res/manhattan-small-sepia.png

  load res/manhattan-small.png
  mosaic
  save res/manhattan-mosaic-default.png
  
  load res/manhattan-small.png
  mosaic 10000
  save res/manhattan-mosaic-default.png
___
 Commands associated with generate. 
- generate  command that will generate image

- Commands: rainbow, checkerboard, 
 
 flags: 
-  -f greece, france, switzerland 
-  -h height int 
-  -w width int 
-  -v vertical striped rainbow 
-  -s small 500px 
-  -m medium 1000px 
-  -l large 2000px 
-  -o output file string 

  Examples: 
-  generate rainbow -w 100 -h 1000 -o res/rainbow.png 
-  generate rainbow -v -o res/verticalrainbow.png
-  generate checkerboard -h 50 -w 50 -o res/checkerboard.png 
-  generate -f greece -l -o res/greece.png
-  generate -f france -o res/france.png 
-  generate -f switzerland -o -m res/switzerland.png 
