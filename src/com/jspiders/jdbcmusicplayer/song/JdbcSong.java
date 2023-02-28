package com.jspiders.jdbcmusicplayer.song;

public class JdbcSong {

		private int SongId;
		private String SongName;
		private String SingerName;
		private String Album;
		private double Duration;
		
		public JdbcSong(int SongId, String SongName, String SingerName, String Album, double Duration) {
			
			this.SongId = SongId;
			this.SongName = SongName;
			this.SingerName = SingerName;
			this.Album = Album;
			this.Duration = Duration;
		}
		
		public JdbcSong() {
			
		}

		public int getSongId() {
			return SongId;
		}

		public void setSongId(int songId) {
			SongId = songId;
		}

		public String getSongName() {
			return SongName;
		}

		public void setSongName(String songName) {
			SongName = songName;
		}

		public String getSingerName() {
			return SingerName;
		}

		public void setSingerName(String singerName) {
			SingerName = singerName;
		}

		public String getAlbum() {
			return Album;
		}

		public void setAlbum(String album) {
			Album = album;
		}

		public double getDuration() {
			return Duration;
		}

		public void setDuration(double duration) {
			Duration = duration;
		}

		@Override
		public String toString() {
			return "JdbcSong [SongId=" + SongId + ", SongName=" + SongName + ", SingerName=" + SingerName + ", Album="
					+ Album + ", Duration=" + Duration + "]";
		}
		
		
}
