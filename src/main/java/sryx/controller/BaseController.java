package sryx.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class BaseController {
	
		protected Logger logger = Logger.getLogger(this.getClass());
		
		/**
		 * 向客户端输出
		 * @param response
		 * @param json
		 */
		protected void writeJsonToResponse(HttpServletResponse response,String json) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.println(json);
				writer.flush();
			} catch (IOException e) {
				logger.error("nsy-auth::writeJsonToResponse::输出异常"+json,e);
			} finally {
				if(null != writer) {
					writer.close();
				}
			}
		}
}