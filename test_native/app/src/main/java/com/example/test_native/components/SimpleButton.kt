import android.content.Context
import android.util.AttributeSet
import android.widget.Button

class CustomButton(context: Context, attrs: AttributeSet) : Button(context, attrs) {
    init {
        val text = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "text")
        setText(text)
    }
}
