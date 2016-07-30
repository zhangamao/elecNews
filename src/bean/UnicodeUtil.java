package bean;

public class UnicodeUtil {

	/**
	 * 用来处理URL地址中--中文的转换
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(c >= 0 && c <= 255){
				sb.append(c);
			}else{
				byte[] b;
				try{
					b = String.valueOf(c).getBytes("utf-8");
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					b = new byte[0];
				}
				for(int j = 0; j < b.length; j++){
					int k = b[j];
					if(k < 0)
						k+=256;
					sb.append("%"+Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
}
