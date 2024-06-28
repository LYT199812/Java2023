package test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.mvc.analyze.dao.UserDaoResposity;
import spring.mvc.analyze.entity.SalesData;

public class Main {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/springmvc-servlet.xml");
		
		SalesData salesData = ctx.getBean("salesData", SalesData.class);
		
		        List<SalesData> salesDatas = new ArrayList<>();
		        // 假設有一個 SalesData 的類別，包含需要的屬性和 getter/setter 方法

		        // 原始的 salesData 數據

		        List<SalesData> analyzeSalesDatas = salesDatas.stream()
		                .collect(Collectors.groupingBy(s -> s.getEcommerce() + s.getProductId() + s.getMonth(),
		                        Collectors.summarizingInt(SalesData::getEcQuantity)))
		                .entrySet()
		                .stream()
		                .map(entry -> {
		                    SalesData result = new SalesData();
		                    result.setEcommerce(entry.getKey().split("_")[0]); // 假設名為 ecommerce
		                    result.setProductId(entry.getKey().split("_")[1]); // 假設名為 productId
		                    result.setMonth(entry.getKey().split("_")[2]); // 假設名為 month
		                    result.setEcQuantity((int) entry.getValue().getSum());

		                    // 計算銷售額
		                    List<SalesData> groupedSales = salesDatas.stream()
		                            .filter(s -> s.getEcommerce().equals(result.getEcommerce())
		                                    && s.getProductId().equals(result.getProductId())
		                                    && s.getMonth().equals(result.getMonth()))
		                            .collect(Collectors.toList());

		                    int totalSales = groupedSales.stream().mapToInt(SalesData::getSalesFigures).sum();
		                    result.setTotalSales(totalSales);

		                    return result;
		                })
		                .collect(Collectors.toList());

		        // 輸出 analyzeSalesDatas，這是按照電商平台、產品ID和月份合併的結果
		        System.out.println(analyzeSalesDatas);
		    }
		}

		
		
	}

}
