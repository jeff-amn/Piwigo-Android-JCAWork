# Piwigo-Android-JCAWork
My Piwigo Android Test Code

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
				   

