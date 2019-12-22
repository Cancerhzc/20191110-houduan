package com.youareright.controller.sys;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.service.sys.PathService;
import com.youareright.utils.FileProcess;
class LocationInfo {
	List<Integer> xStart;
	List<Integer> yStart;
	List<Integer> xEnd;
	List<Integer> yEnd;
	public List<Integer> getxStart() {
		return xStart;
	}
	public List<Integer> getyStart() {
		return yStart;
	}
	public List<Integer> getxEnd() {
		return xEnd;
	}
	public List<Integer> getyEnd() {
		return yEnd;
	}
}


@RestController
public class XMLProcessController {
	@Resource(name = "pathServiceImpl")
	private PathService pathService;
	
	private FileProcess fileProcess=new FileProcess();
	
	@PostMapping("/XMLProcess")
	public String XMLProcess(@RequestBody LocationInfo locationInfo) {
		List<Integer> xStart=locationInfo.getxStart();
		List<Integer> yStart=locationInfo.getyStart();
		List<Integer> xEnd=locationInfo.getxEnd();
		List<Integer> yEnd=locationInfo.getyEnd();
//		fileProcess.deleteFile(pathService.runningPath().getPath()+"/XMLTempPath");
		fileProcess.makeDirectory(pathService.runningPath().getPath()+"/XMLTempPath");
		String thisUUID= UUID.randomUUID().toString().replaceAll("-","");
		String XMLPath=pathService.runningPath().getPath()+"/XMLTempPath/"+thisUUID+".xml";
		String returnPath="/XMLTempPath/"+thisUUID+".xml";
		String content=new String();
		content+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+'\n';
		content+="<shelf>"+"\n";
		int number=xStart.size();
		for(int i=0;i<number;i++) {
			content+="\t<layer>\n";
			content+="\t\t<x1>"+Integer.toString(xStart.get(i))+"</x1>\n";
			content+="\t\t<y1>"+Integer.toString(yStart.get(i))+"</y1>\n";
			content+="\t\t<x2>"+Integer.toString(xEnd.get(i))+"</x2>\n";
			content+="\t\t<y2>"+Integer.toString(yStart.get(i))+"</y2>\n";
			content+="\t\t<x3>"+Integer.toString(xStart.get(i))+"</x3>\n";
			content+="\t\t<y3>"+Integer.toString(yEnd.get(i))+"</y3>\n";
			content+="\t\t<x4>"+Integer.toString(xEnd.get(i))+"</x4>\n";
			content+="\t\t<y4>"+Integer.toString(yEnd.get(i))+"</y4>\n";
			content+="\t</layer>\n";
		}
		content+="</shelf>";
		fileProcess.writeFile(content,XMLPath);
		return returnPath;
	}
	
}
