package kr.or.ddit.mvc.basic;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.mvc.vo.LprodVO;

// JDBC예제 중 JdbcTest05.java를 iBatis용으로 변경해보시오.
// Mapper파일명 : jdbc-mapper.xml

public class JdbcToIbatisTest {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		SqlMapClient smc = null;
		
		try {
			
			Charset charset = Charset.forName("utf-8");
			Resources.setCharset(charset);
			
			Reader rd = Resources.getResourceAsReader(
					"kr/or/ddit/ibatis/config/sqlMapConfig.xml");
			
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			
			rd.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			
			System.out.println("[데이터 추가하기]");
			
			String gu = "";
			
			LprodVO lvo1;
			do {
			System.out.print("상품 분류 코드(LPROD_GU) 입력 : ");
			gu = scan.nextLine();
			lvo1 = (LprodVO)smc.queryForObject("lprod.searchId", gu);
			
			
			if(lvo1.getLprod_gu() == null) {
				System.out.println("중복된 상품 코드가 있습니다. 다시 입력");
			}
			
			}while(lvo1.getLprod_gu() != null);
			
				System.out.println("insert 작업 시작");
				System.out.print("lprod_id 입력 >> ");
				int id = scan.nextInt();
				
				System.out.print("lprod_nm 입력 >> ");
				String nm = scan.next();
				
				LprodVO lvo2 = new LprodVO();
				lvo2.setLprod_gu(gu);
				lvo2.setLprod_id(id);
				lvo2.setLprod_nm(nm);
				
				Object obj = smc.insert("lprod.insertLprod", lvo2);
			
				if(obj == null) {
					System.out.println("insert작업 성공 !");
				}else {
					System.out.println("insert작업 실패 . . .");
				}
				System.out.println("----------------------------------------");
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
