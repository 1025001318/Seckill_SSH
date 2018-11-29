package com.seckill.web;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResult;
import com.seckill.entity.Seckill;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.service.SeckillService;

/**
 * 控制层
 * @author Administrator
 *
 */
@RequestMapping("/sec")
@Controller
public class SeckillController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService service;
	
	/**
	 * 获取列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = service.getSeckillList();
		System.out.println(list);
		model.addAttribute("list",list);
		return "list";
	}
	
	/**
	 * 获取详情页
	 * @param seckillId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Long seckillId,Model model) {
		if(seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = service.getById(seckillId);
		if(seckill == null) {
			return "forword:/seckill/list";
		}
		model.addAttribute("seckill",seckill);
		return "detail";
	}
	/**
	 * 返回秒杀商品状态
	 * @param seckillId
	 * @return
	 */
	//ajax  json
	@RequestMapping(value="/{seckillId}/exposer",
			method=RequestMethod.POST,
			produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId")Long seckillId ){
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = service.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true,exposer);
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false,e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取秒杀结果
	 * @param seckillId
	 * @param md5
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Long seckillId,
													@PathVariable("md5")String md5,
													@CookieValue(value = "userPhone",required = false)Long phone){
		if(phone == null) {
			return new SeckillResult<SeckillExecution>(false,"未注册");
		}
		try {
			SeckillExecution execution = service.executionSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true,execution); 
		} catch (SeckillCloseException e) {
			SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(false,execution);
		} catch (RepeatKillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(false,execution);
		} catch(Exception e) {
			SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(false,execution);
		}
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	@RequestMapping(value="/time/now",method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time(){
		Date now = new Date();
		return new SeckillResult<Long>(true,now.getTime());
	}
}
