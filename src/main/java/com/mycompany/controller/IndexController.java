package com.mycompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	SocketLiveServerWorker liveWorker;
	SocketLiveServerWorker infoWorker;

	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ModelAndView status() {
		ModelAndView view = new ModelAndView("status");
		if(!(liveWorker!=null && liveWorker.isWorking()) || !(infoWorker!=null && infoWorker.isWorking())) {
			return new ModelAndView("redirect:index");
		}
		view.addObject("live", liveWorker.isWorking());
		view.addObject("info", infoWorker.isWorking());
		return view;
	}

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public String start(@RequestParam(value = "live_port") Integer livePort
			, @RequestParam(value = "info_port") Integer infoPort) {
		liveWorker = new SocketLiveServerWorker(livePort);
		liveWorker.startWorker();
		infoWorker = new SocketLiveServerWorker(infoPort);
		infoWorker.startWorker();
		return "redirect:status";
	}

	@RequestMapping(value = "/view/{type}", method = RequestMethod.GET)
	public ModelAndView info(@PathVariable String type) {
		ModelAndView view = new ModelAndView("view");
		view.addObject("type", type);
		return view;
	}

	@RequestMapping(value = "/view/{type}", method = RequestMethod.POST)
	public ModelAndView push(@PathVariable String type, @RequestParam("message") String mes) {
		ModelAndView view = new ModelAndView("view");
		System.out.println(mes);
		if("live".equals(type)) {
			if(!(liveWorker!=null && liveWorker.isWorking())) {
				return new ModelAndView("redirect:status");
			}
			liveWorker.push(mes);
		}
		if("info".equals(type)) {
			if(!(infoWorker!=null && infoWorker.isWorking())) {
				return new ModelAndView("redirect:status");
			}
			infoWorker.push(mes);
		}
		view.addObject("type", type);
		return view;
	}
}
