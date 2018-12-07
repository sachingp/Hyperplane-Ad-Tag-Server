package com.ad.server.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ad.server.pojo.Account;
import com.ad.server.pojo.Advertiser;
import com.ad.server.pojo.Campaign;
import com.ad.server.pojo.Creative;
import com.ad.server.pojo.Status;
import com.ad.server.repo.AccountRepo;
import com.ad.server.repo.AdvertiserRepo;
import com.ad.server.repo.CampaignRepo;
import com.ad.server.repo.CreativeRepo;

@RestController
public class CreativeResource {

	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private AdvertiserRepo advRepo;	
	
	@Autowired
	private CampaignRepo campaignRepo;	

	@Autowired
	private CreativeRepo creativeRepo;
	
	@GetMapping("/accounts")
	public List<Account> getActiveAccounts(@RequestParam("status") Integer statusId) {
		Status statusObj = new Status();
		statusObj.setStatusId(statusId);
	    return accountRepo.findActiveAccounts(statusObj);
	}
	
	@GetMapping("/advertisers")
	public List<Advertiser> getActiveAdvertisers(@RequestParam("accountId") Integer accountId) {
	    return advRepo.findActiveAdvertiserForAccount(accountId);
	}
	
	@GetMapping("/campaigns")
	public List<Campaign> getActiveCampaigns(@RequestParam("advertiserId") Integer advertiserId) {
	    return campaignRepo.findActiveCampaignsForAdvertiser(advertiserId);
	}
	
	@GetMapping("/creatives")
	public List<Creative> getActiveCreatives(@RequestParam("campaignId") Integer campaignId) {
	    return creativeRepo.findActiveCreativesForCampaigns(campaignId);
	}
	@GetMapping("/eligibleCreatives")
	public List<Creative> getAll() {
	    return creativeRepo.findAllMatchingCreativesList();
	}
}
