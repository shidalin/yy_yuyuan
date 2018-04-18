package nc.pub.yuyuan.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;

public class FileUtil {

	String nchome = nc.bs.framework.common.RuntimeEnv.getInstance().getNCHome();
	String fileLacation = nchome + "/wmslogs";

	public void outputFile(String param, String type,String path) throws BusinessException {
		if (param != null && !"".equals(param)) {

			String fileName = type + "_" + (new UFDate()).toStdString()
					+ ".txt";
			try {
				File fileDir = new File(fileLacation+path);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				File file = new File(fileDir, fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				RandomAccessFile raf = new RandomAccessFile(file.getPath(),
						"rw");
				raf.write(param.getBytes("utf-8"));
				raf.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		FileUtil fileUtil = new FileUtil();
		try {
			fileUtil.outputFile("ÉÏº£»¶Ó­Äú", "bd_material","");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
