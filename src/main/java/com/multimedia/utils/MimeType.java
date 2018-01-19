package com.multimedia.utils;

public final class MimeType {
    private static final String TYPE_VIDEO = "video";
    private static final String TYPE_AUDIO = "audio";
    private static final String TYPE_TEXT = "text";
    private static final String TYPE_APPLICATION = "application";

    /**
     * video codec
     */
    public static final String VIDEO_H263 = appendVideo("3gpp");
    public static final String VIDEO_H264 = appendVideo("avc");
    public static final String VIDEO_H265 = appendVideo("hevc");
    public static final String VIDEO_VP8 = appendVideo("x-vnd.on2.vp8");
    public static final String VIDEO_VP9 = appendVideo("x-vnd.on2.vp9");
    public static final String VIDEO_MPEG4 = appendVideo("mp4v-es");

    /**
     * audio codec
     */
    public static final String AUDIO_AAC = appendAudio("mp4a-latm");
    public static final String AUDIO_MPEG = appendAudio("mpeg");
    public static final String AUDIO_MPEG_LAYER1 = appendAudio("mpeg-L1");
    public static final String AUDIO_MPEG_LAYER2 = appendAudio("mpeg-L2");
    public static final String AUDIO_G711_ALAW = appendAudio("g711-alaw");
    public static final String AUDIO_G711_MLAW = appendAudio("g711-mlaw");
    public static final String AUDIO_AC3 = appendAudio("ac3");
    public static final String AUDIO_EAC3 = appendAudio("eac3");
    public static final String AUDIO_VORBIS = appendAudio("vorbis");
    public static final String AUDIO_OPUS = appendAudio("opus");
    public static final String AUDIO_AMR_NB = appendAudio("3gpp");
    public static final String AUDIO_AMR_WB = appendAudio("amr-wb");
    public static final String AUDIO_FLAC = appendAudio("flac");
    public static final String AUDIO_RAW = appendVideo("L16");

    /**
     * subtitle text
     */
    public static final String TEXT_VTT = appendText("vtt");
    public static final String TEXT_3GPP = appendText("3gpp-tt");
    public static final String TEXT_SUBRIP = appendApplication("x-subrip");
    public static final String TEXT_ASS = appendText("x-ass");

    /**
     * container
     */
    public static final String VIDEO_MP4 = appendVideo("mp4");
    public static final String VIDEO_WEBM = appendVideo("webm");
    public static final String VIDEO_MKV = appendVideo("x-matroska");
    public static final String APPLICATION_OGG = appendApplication("ogg");
    public static final String VIDEO_AVI = appendVideo("avi");
    public static final String VIDEO_MPEG = appendVideo("mpeg");
    public static final String VIDEO_MPEG2TS = appendVideo("mp2t");
    public static final String VIDEO_FLV = appendVideo("x-flv");
    public static final String AUDIO_WAV = appendAudio("x-wav");

    /**
     * live streaming
     */
    public static final String APPLICATION_M3U8 = appendApplication("x-mpegURL");
    public static final String APPLICATION_M3U8_NEW = appendApplication("vnd.apple.mpegurl");
    public static final String APPLICATION_MPD = appendApplication("dash+xml");

    private MimeType() {
        //nothing
    }

    private static String appendVideo(String suffix) {
        return TYPE_VIDEO + "/" + suffix;
    }

    private static String appendAudio(String suffix) {
        return TYPE_AUDIO + "/" + suffix;
    }

    private static String appendText(String suffix) {
        return TYPE_TEXT + "/" + suffix;
    }

    private static String appendApplication(String suffix) {
        return TYPE_APPLICATION + "/" + suffix;
    }

    public static boolean isAudio(String mimeType) {
        if (mimeType.equals(AUDIO_AAC)
                || mimeType.equals(AUDIO_MPEG)
                || mimeType.equals(AUDIO_MPEG_LAYER1)
                || mimeType.equals(AUDIO_MPEG_LAYER2)
                || mimeType.equals(AUDIO_G711_ALAW)
                || mimeType.equals(AUDIO_G711_MLAW)
                || mimeType.equals(AUDIO_AC3)
                || mimeType.equals(AUDIO_EAC3)
                || mimeType.equals(AUDIO_VORBIS)
                || mimeType.equals(AUDIO_OPUS)
                || mimeType.equals(AUDIO_AMR_NB)
                || mimeType.equals(AUDIO_AMR_WB)
                || mimeType.equals(AUDIO_FLAC)
                || mimeType.equals(AUDIO_RAW)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isVideo(String mimeType) {
        if (mimeType.equals(VIDEO_H263)
                || mimeType.equals(VIDEO_H264)
                || mimeType.equals(VIDEO_H265)
                || mimeType.equals(VIDEO_VP8)
                || mimeType.equals(VIDEO_VP9)
                || mimeType.equals(VIDEO_MPEG4)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isSubtitle(String mimeType) {
        if (mimeType.equals(TEXT_VTT)
                || mimeType.equals(TEXT_3GPP)
                || mimeType.equals(TEXT_SUBRIP)
                || mimeType.equals(TEXT_ASS)) {
            return true;
        }
        else {
            return false;
        }
    }
}
