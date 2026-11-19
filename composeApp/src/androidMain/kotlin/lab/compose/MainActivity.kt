package lab.compose

import android.os.Bundle
import androidx.activity.ComponentActivity

/**
 * M15. Android: o ponto de entrada é uma Activity que hospeda o mesmo `App()` do código comum.
 * Em `onCreate`, chame `super.onCreate(...)` e depois `setContent { App() }`
 * (`androidx.activity.compose.setContent`). O AndroidManifest.xml já está pronto.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        TODO("M15: hospede o App() com setContent")
    }
}
