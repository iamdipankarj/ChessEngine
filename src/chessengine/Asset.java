package chessengine;

import java.awt.image.BufferedImage;

public class Asset {
	
	private static final int width = 64;
	private static final int height = 64;
	
	public static class WHITE {
		public static BufferedImage king, queen, bishop, rook, pawn, knight;
	}
	
	public static class BLACK {
		public static BufferedImage king, queen, bishop, rook, pawn, knight;
	}

	public static void init(String src) {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage(src));

		WHITE.king = sheet.crop(0, 0, width, height);
		WHITE.queen = sheet.crop(64, 0, width, height);
		WHITE.rook = sheet.crop(128, 0, width, height);
		WHITE.bishop = sheet.crop(192, 0, width, height);
		WHITE.knight = sheet.crop(256, 0, width, height);
		WHITE.pawn = sheet.crop(320, 0, width, height);
		
		BLACK.king = sheet.crop(0, 64, width, height);
		BLACK.queen = sheet.crop(64, 64, width, height);
		BLACK.rook = sheet.crop(128, 64, width, height);
		BLACK.bishop = sheet.crop(192, 64, width, height);
		BLACK.knight = sheet.crop(256, 64, width, height);
		BLACK.pawn = sheet.crop(320, 64, width, height);
	}
	
}
