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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public ModelAndView start(@RequestParam(value = "live_port") Integer livePort
			, @RequestParam(value = "info_port") Integer infoPort) {
		ModelAndView view = new ModelAndView("start");
		liveWorker = new SocketLiveServerWorker(livePort);
		liveWorker.startWorker();
		infoWorker = new SocketLiveServerWorker(infoPort);
		infoWorker.startWorker();

		return view;
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
			liveWorker.push(mes);
		}
		if("info".equals(type)) {
			infoWorker.push(mes);
		}
		view.addObject("type", type);
		return view;
	}
}
