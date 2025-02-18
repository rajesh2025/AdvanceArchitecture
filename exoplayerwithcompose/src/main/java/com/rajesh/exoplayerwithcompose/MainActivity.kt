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
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashChunkSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.dash.DefaultDashChunkSource
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DefaultDrmSessionManagerProvider
import androidx.media3.exoplayer.drm.DrmSessionManager
import androidx.media3.exoplayer.drm.HttpMediaDrmCallback
import androidx.media3.exoplayer.drm.MediaDrmCallback
import androidx.media3.exoplayer.drm.WidevineUtil
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import androidx.media3.ui.PlayerView
import com.rajesh.exoplayerwithcompose.ui.theme.AdvanceArchitectureTheme
import java.util.UUID


class MainActivity : ComponentActivity() {
    private lateinit var playerView: ExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdvanceArchitectureTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WidevineDRMContent(innerPadding)
//                    ExoPlayerView(
//                        videoUrl = "https://storage.googleapis.com/shaka-demo-assets/angel-one-widevine/dash.mpd",
//                        licenseUrl = "https://cwip-shaka-proxy.appspot.com/no_auth",
//                        isDrmEnabled = true,
//                        innerPadding = innerPadding
//                    )


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
        ////                    ExoPlayerView("https://bitmovin-a.akamaihd.net/content/sintel/hls/playlist.m3u8",innerPadding)
        //                    // videoUrl = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd",
        //                    //        licenseUrl = "https://proxy.uat.widevine.com/proxy?provider=widevine_test"
        //                    //
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

    @OptIn(UnstableApi::class)
    @Composable
    fun WidevineDRMContent(innerPadding: PaddingValues) {
        val context = LocalContext.current
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent(USER_AGENT)
            .setTransferListener(
                DefaultBandwidthMeter.Builder(context)
                    .setResetOnNetworkTypeChange(false)
                    .build()
            )

        val dashChunkSourceFactory: DashChunkSource.Factory = DefaultDashChunkSource.Factory(
            defaultHttpDataSourceFactory
        )

        val manifestDataSourceFactory = DefaultHttpDataSource.Factory().setUserAgent(USER_AGENT)

        val dashMediaSource =
            DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory)
                .createMediaSource(
                    MediaItem.Builder()
                        .setUri(Uri.parse(URL))
                        // DRM Configuration
                        .setDrmConfiguration(
                            MediaItem.DrmConfiguration.Builder(drmSchemeUuid)
                                .setLicenseUri(DRM_LICENSE_URL).build()
                        )
                        .setMimeType(MimeTypes.APPLICATION_MPD)
                        .setTag(null)
                        .build()
                )

         ExoPlayer.Builder(context).setSeekForwardIncrementMs(10000)
            .setSeekBackIncrementMs(10000)
            .build().also { playerView = it }
        playerView.apply {
            this.playWhenReady = true
            setMediaSource(dashMediaSource, true)
            prepare()
        }


        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = { viewContext ->
                PlayerView(viewContext)
                    .apply {
                        player = playerView
                    }
            },
            update = { playerView ->
                playerView.player = this.playerView
            }
        )

        DisposableEffect(Unit) {
            onDispose {
                playerView.release()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        playerView.playWhenReady = false
    }
    companion object {
        private const val URL =
            "https://bitmovin-a.akamaihd.net/content/art-of-motion_drm/mpds/11331.mpd"
        private const val DRM_LICENSE_URL =
            "https://proxy.uat.widevine.com/proxy?provider=widevine_test"
        private const val USER_AGENT = "ExoPlayer-Drm"
        private val drmSchemeUuid = C.WIDEVINE_UUID // DRM Type
    }
}



