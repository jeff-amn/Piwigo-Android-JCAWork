# Piwigo-Android-JCAWork
My Piwigo Android Test Code
9-30-17

realized that "photos" was not consistent with the project so changed use of "photo" to "images"

** worked on code for images fragment, ImagesRepository, ImagesViewModel and ImagesItemViewModel

** I can now get a list of Images from piwigo website

How to get an image list from a piwigo album

  a. use /tools/ws.htm and select categories.getList (return json)
  
  b. hardcode the catID for the album in ImagesViewModel -> loadImages
  
  c. put a breakpoint at line 85 of ImagesViewModel (images.addAll(imagelist) in onNext()
  
  d. run app and select Menu - > Albums
  
  e. the code will break at line 85 and the contents of the variable imagelist can be seen
  

 TO DO Next:  need to figure out how to bind the imagelist so the images appear on the screen


----------------------------------------------------------------------------------------------------------------------
9-26-17
Working on being able to display images from an album

1) when a displayed album is touched - onViewAlbumPhotos() in AlbumItemViewModel is called
       which is made possible from this line in layout/item_album.xml:
               	   android:onClick="@{() -> viewModel.onViewAlbumPhotos()}"
				   
2) added getImages in io\RestService.java
3) added ImageListResponse in io\model\  uses existing ImageInfo
4) added layout\fragment_photos.xml
5) added layout\item_photos.xml
6) added ui\fragment\PhotosFragment
7) added ui\viewmodel\PhotosViewModel
8) added ui\viewmodel\PhotosItemViewModel
9) added io\repository\ImageRepository

thinking that there should be 2 fragments -> 1 for albums and 1 for photos of a selected album
see this website - https://developer.android.com/training/basics/fragments/fragment-ui.html


To Do next - ImageRepository code - how to code this -- .getImages(categoryId) is right, not sure how to get the data into a List<ImageInfo> so it can be returned
                   and the .compose gives an error
				   

