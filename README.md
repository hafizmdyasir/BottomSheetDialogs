# BottomSheetDialogs
> A lightweight Android library to create BottomSheetDialogs with more functionalities than Android\'s own implementation.

## Implementation in Gradle
The implementation for this library is straightforward. First of all, add the Jitpack respository to your build.gradle file
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Next, include this library like so:
```
implementation 'com.github.HafizYasir:BottomSheetDialogs:1.0.1'
```
Replace 1.0.1 with whatever the latest version is.
## How To
You can treat each component of the app (see [list of available components](#listOfAvailable)) as a BottomSheetDialogFragment. Each component has a factory method you can use to create an instance and then call the `BottomSheetDialog.show()` method, passing in a `FragmentManager` as the first argument.

The factory methods in each component have various arguments that are self-explanatory. An example is included in this ReadMe file too. Also, there is a sample app included.

## Example
If you want to create an indeterminate progress dialog, use the `ProgressSheet.getInstance()` method from the `ProgressSheet` component. This method takes in the following arguments:
1. A string resource for the dialog title.
2. A string resource for the dialog message.
3. A boolean parameter to determine whether to show the cancel button below the message.
4. Another boolean parameter to determine whether the progress bar should be indeterminate or not.
An overloaded `ProgressSheet.getInstance()` method takes in String arguments instead of `int` resources to set the dialog title and message.
You can set a `View.OnClickListener` using the `ProgressSheet.setClickListener()` method to listen to button presses.
If you have chosen the indeterminate attribute to be false, the `ProgressSheet.publishProgress()` method can be called. The parameter `progress` of this method must be an integer value between 0 to 100.
### Code Sample
```java
ProgressSheet mProgressSheet = ProgressSheet.getInstance(R.string.title, R.string.message, showCancelButton, indeterminate);
//If you have chosen to show the cancel button
mProgressSheet.setOnClickListener(v -> handleCancelClick());
mProgressSheet.show(getSupportFragmentManager, "SOME TAG");
/*If you have chosen the progress bar to not be indeterminate, use the following sample to publish a progress. If the dialog view is not visible, the method call will be ignored.*/
mProgressSheet.publishProgress(progressValue);
```
A similar strategy may be followed for displaying a message dialog.


## <span id=listOfAvailable>Available Components</span>
For now, I only had the time to create a progress dialog and a message dialog. I am hoping to add other options in the near future.
