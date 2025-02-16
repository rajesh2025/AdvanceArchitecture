package com.rajesh.exoplayerwithcompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DefaultDrmSessionManagerProvider
import androidx.media3.exoplayer.drm.DrmSessionManager
import androidx.media3.exoplayer.drm.HttpMediaDrmCallback
import androidx.media3.exoplayer.drm.MediaDrmCallback
import androidx.media3.exoplayer.drm.WidevineUtil
import androidx.media3.ui.PlayerView
import com.rajesh.exoplayerwithcompose.ui.theme.AdvanceArchitectureTheme
import java.util.UUID


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdvanceArchitectureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ExoPlayerView("https://bitmovin-a.akamaihd.net/content/sintel/hls/playlist.m3u8",innerPadding)
                    // videoUrl = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd",
                    //        licenseUrl = "https://proxy.uat.widevine.com/proxy?provider=widevine_test"
                    //
                    ExoPlayerView(
                        videoUrl = "https://storage.googleapis.com/shaka-demo-assets/angel-one-widevine/dash.mpd",
                        licenseUrl = "https://cwip-shaka-proxy.appspot.com/no_auth",
                        isDrmEnabled = true,
                        innerPadding = innerPadding
                    )
                }
            }
        }

    }

    @OptIn(UnstableApi::class)
    @Composable
    fun ExoPlayerView(
        videoUrl: String,
        innerPadding: PaddingValues,
        licenseUrl: String,
        isDrmEnabled: Boolean
    ) {
        val context = LocalContext.current

        val drmMediaItem = MediaItem.Builder().setUri(videoUrl)
            .setDrmUuid(Util.getDrmUuid("widevine"))
            .setDrmLicenseUri(licenseUrl)
            .build()
        val exoplayer = ExoPlayer.Builder(context).build()


        exoplayer.setMediaItem(
            if (isDrmEnabled) drmMediaItem else MediaItem.fromUri(
                Uri.parse(
                    videoUrl
                )
            )
        )
        val drmSessionManagerProvider = DefaultDrmSessionManagerProvider().apply {
            setDrmHttpDataSourceFactory(DefaultHttpDataSource.Factory())
        }
        exoplayer.prepare()
        exoplayer.playWhenReady = true

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = { viewContext ->
                PlayerView(viewContext)
                    .apply {
                        player = exoplayer
                    }
            },
            update = { playerView ->
                playerView.player = exoplayer
            }
        )

        DisposableEffect(Unit) {
            onDispose {
                exoplayer.release()
            }
        }
    }

}



