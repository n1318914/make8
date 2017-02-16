package com.yundaren.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;

/**
 * 图片验证码生成工具类
 */
@Slf4j
public abstract class ImageCodeUtil {

	private static final String RANDOM_CODE_KEY = "CAPTCHA"; // 验证码放在session中的key

	private static final int CODE_NUM = 4; // 验证码字符个数

	// 设置图形验证码中字符串的字体和大小
	private static Font myFont = new Font("Arial", Font.BOLD, 20);

	// 随机字符数组
	private static char[] charSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

	private static Random random = new Random();

	/**
	 * 生成随机验证码
	 * 
	 * @param request
	 * @param response
	 */
	public static void createRandomCode(HttpServletRequest request, HttpServletResponse response) {
		// 阻止生成的页面内容被缓存，保证每次重新生成随机验证码
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 指定图形验证码图片的大小
		int width = 80, height = 50;
		// 生成一张新图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 在图片中绘制内容
		Graphics2D g = image.createGraphics();
		g.setColor(getRandomColor(200, 250));
		g.setColor(new Color(255, 255, 255));
		g.fillRect(1, 1, width - 1, height - 1);
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(myFont);

		// 绘制干扰线
		g.setColor(getRandomColor(180, 220));
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x, y, x + xl + 40, y + yl + 20);
		}

//		// 添加噪点
//		float yawpRate = 0.02f;// 噪声率
//		int area = (int) (yawpRate * width * height);
//		for (int i = 0; i < area; i++) {
//			int x = random.nextInt(width);
//			int y = random.nextInt(height);
//			int rgb = getRandomIntColor();
//			image.setRGB(x, y, rgb);
//		}

		// 该变量用来保存系统生成的随机字符串
		StringBuilder sRand = new StringBuilder(CODE_NUM);
		for (int i = 0; i < CODE_NUM; i++) {
			// 取得一个随机字符
			String tmp = getRandomChar();
			sRand.append(tmp);
			// 将系统生成的随机字符添加到图形验证码图片上
			g.setColor(new Color(50 + random.nextInt(110), 50 + random.nextInt(110), 50 + random.nextInt(110)));
			AffineTransform affine = new AffineTransform();
			affine.setToRotation(Math.PI / 10 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
					(width / CODE_NUM) * i + myFont.getSize() / 2, height / 2);
			g.setTransform(affine);
			g.drawString(tmp, 15 * i + 10, (height - myFont.getSize()) / 2 + myFont.getSize());
		}

		// 取得用户Session
		HttpSession session = request.getSession(true);
		// 将系统生成的图形验证码添加
		session.setAttribute(RANDOM_CODE_KEY, sRand.toString());
		g.dispose();
		// 输出图形验证码图片
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成随机验证码图片
	 */
	public static void genVCodeImage() {
		// 指定图形验证码图片的大小
		int width = 80, height = 50;
		// 生成一张新图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 在图片中绘制内容
		Graphics2D g = image.createGraphics();
		g.setColor(getRandomColor(200, 250));
		g.setColor(new Color(255, 255, 255));
		g.fillRect(1, 1, width - 1, height - 1);
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(myFont);

		// 绘制干扰线
		g.setColor(getRandomColor(180, 220));
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x, y, x + xl + 40, y + yl + 20);
		}

		// 添加噪点
		float yawpRate = 0.02f;// 噪声率
		int area = (int) (yawpRate * width * height);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}

		// 该变量用来保存系统生成的随机字符串
		StringBuilder sRand = new StringBuilder(CODE_NUM);
		for (int i = 0; i < CODE_NUM; i++) {
			// 取得一个随机字符
			String tmp = getRandomChar();
			sRand.append(tmp);
			// 将系统生成的随机字符添加到图形验证码图片上
			g.setColor(new Color(50 + random.nextInt(110), 50 + random.nextInt(110), 50 + random.nextInt(110)));
			AffineTransform affine = new AffineTransform();
			affine.setToRotation(Math.PI / 10 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
					(width / CODE_NUM) * i + myFont.getSize() / 2, height / 2);
			g.setTransform(affine);
			g.drawString(tmp, 15 * i + 10, (height - myFont.getSize()) / 2 + myFont.getSize());
		}

		g.dispose();
		// 输出图形验证码图片
		try {
			ImageIO.write(image, "JPEG", new File("D:\\1.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 检查用户输入的验证码是否正确
	 * 
	 * @param request
	 * @param inputCode
	 * @return true: 正确, false:不正确
	 */
	public static boolean checkRandomCode(HttpServletRequest request, String inputCode) {
		HttpSession session = request.getSession(false);
		if (session != null && StringUtils.hasLength(inputCode)) {
			String code = (String) session.getAttribute(RANDOM_CODE_KEY);
			log.info("inputCode:" + inputCode.trim() + ",code:" + code);
			removeRandomCode(request);
			return inputCode.trim().equalsIgnoreCase(code);
		}
		return false;
	}

	/**
	 * 移除验证码
	 * 
	 * @param request
	 * @param inputCode
	 */
	public static void removeRandomCode(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(RANDOM_CODE_KEY);
		}
	}

	// 生成随机颜色
	private static Color getRandomColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	// 随机生成一个字符
	private static String getRandomChar() {
		int index = random.nextInt(charSequence.length);
		return String.valueOf(charSequence[index]);
	}

	private static int getRandomIntColor() {
		int[] rgb = getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}

	private static int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}

	public static void main(String[] args) {
		ImageCodeUtil.genVCodeImage();
	}
}
