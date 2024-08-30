package com.lbstspringboot3.common;

import cn.hutool.core.io.FileUtil;
import com.lbstspringboot3.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class LocalStorage {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${lbst.imgPath}")
	private String localDir;

	private Map<String, String> headImgType;

	{
		headImgType = new HashMap<>(
				Map.of("image/jpeg", "jpg", "image/png", "png", "image/gif", "gif"));
	}

	/**
	 * 获取本地图片保存的目录，如果目录不存在就创建
	 */
	public String localImgDir() {
		Path path = Path.of(localDir);
		// 如果目录存在就返回地址
		if (FileUtil.isDirectory(path.toFile())) {
			return path.toString();
		}
		try {
			FileUtil.mkdir(path);
			return path.toString();
		} catch (Exception e) {
			// 文件创建失败
			return null;
		}
	}

	/**
	 * 获取图片的本地路径
	 *
	 * @param name 图片名字，例如：1.png
	 */
	public String localImgPath(String name) {
		return Path.of(this.localImgDir(), name).toString();
	}

	/**
	 * 保存图片到本地
	 *
	 * @param file 表单数据
	 * @return String 返回保存之后的图片名称
	 */
	public String saveImg(MultipartFile file) {
		String type = file.getContentType();
		if (!headImgType.containsKey(type)) {
			return null;
		}
		type = headImgType.get(type);
		String name = UUIDUtil.getUUIDStringWithoutLine() + "." + type;

		try {
			FileUtil.writeBytes(file.getBytes(), this.localImgPath(name));
			return name;
		} catch (Exception e) {
			// 图片保存失败
			return null;
		}
	}

	/**
	 * 通过图片名称获取图片二进制数据
	 *
	 * @param name 图片名称
	 */
	public byte[] getHeadImgBytes(String name) {
		try {
			return FileUtil.readBytes(Path.of(localImgPath(name)).toFile());
		} catch (Exception e) {
			log.error("头像获取失败");
			return null;
		}
	}

	/**
	 * 通过图片名称获取图片类型
	 *
	 * @param name 图片名称
	 */
	public String getHeadImgType(String name) {
		log.info("-----------------------------------------------");
		log.info(this.localImgPath(name));
		log.info("-----------------------------------------------");
		String type = FileUtil.getType(Path.of(this.localImgPath(name)).toFile());
		if (Set.of("png", "jpg", "gif").contains(type)) {
			if (type.equals("jpg"))
				type = "jpeg";
			return "image/" + type;
		} else
			return null;
	}

	/**
	 * 删除图片
	 *
	 * @param name 图片名称
	 */
	public boolean delHeadImg(String name) {
		if (name != null && !name.isEmpty())
			return FileUtil.del(this.localImgPath(name));
		return false;
	}
}
