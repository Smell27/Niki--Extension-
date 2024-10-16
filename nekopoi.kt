package eu.kanade.tachiyomi.extension.en.myanimelist

import eu.kanade.tachiyomi.source.model.AnimeEpisode
import eu.kanade.tachiyomi.source.model.AnimeSource
import eu.kanade.tachiyomi.source.model.Video
import eu.kanade.tachiyomi.source.online.ParsedAnimeHttpSource
import okhttp3.Response
import org.jsoup.nodes.Document

class MyAnimeList : ParsedAnimeHttpSource() {

    override val name = "nekopoi"
    override val baseUrl = "https://nekopoi.care/"
    override val lang = "en"

    // Mendapatkan daftar episode dari halaman
    override fun episodeListParse(response: Response): List<AnimeEpisode> {
        val document = response.asJsoup()
        val episodes = mutableListOf<AnimeEpisode>()

        document.select("selector_episode").forEach {
            val episode = AnimeEpisode.create()
            episode.name = it.select("title_selector").text()
            episode.url = it.select("url_selector").attr("href")
            episodes.add(episode)
        }
        return episodes
    }

    // Parsing video dari episode yang dipilih
    override fun videoListParse(response: Response): List<Video> {
        val videos = mutableListOf<Video>()
        val document = response.asJsoup()

        document.select("selector_video_url").forEach {
            val videoUrl = it.attr("src")
            videos.add(Video(videoUrl, "Default Quality", videoUrl))
        }
        return videos
    }

    override fun searchAnimeParse(response: Response): AnimeSource {
        // Implement search parsing
    }
}
