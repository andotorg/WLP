package com.farm.util.cache;

 
public enum FarmCacheNames {
	 
	TypeDocsForDocpage("wcp-typedocsfordocpage"),
	 
	UnReadMessagesByUser("wcp-unreadmessagesbyuser"),
	 
	AllSpecial("wcp-allspecial"),
	
	 
	TypeDocs("wcp-typedocs"),
	 
	UserReadTypeids("wcp-userreadtypeids"),
	 
	TypesForWriteDoc("wcp-typesforwritedoc"),
	 
	FqaTypesForWriteDoc("wcp-fqatypesforwritedoc"),
	 
	PopTypesForReadDoc("wcp-poptypesforreaddoc"),
	 
	PopFqaTypesForReadDoc("wcp-popfqatypesforreaddoc"),
	 
	pubTypeids("wcp-pubtypeids"),
	 
	AllSubType("wcp-allsubnode"),
	 
	TypeInfos("wcp-typeinfos"),
	 
	WebUrlList("wcp-weburllist"),
	 
	WebUrlListNoLogin("wcp-weburllistnologin"),
	 
	TypeFqas("wcp-typefqas"),
	 
	NewPublicDocMessages("wcp-newpublicdocmessages"),
	 
	HotQuestionByFinish("wcp-hotquestionbyfinish"),
	 
	HotQuestionByWaiting("wcp-hotquestionbywaiting"),
	 
	HotQuestion("wcp-hotquestion"),
	 
	NewKnowList("wcp-newknowlist"),
	 
	PubHotDoc("wcp-pubhotdoc"),
	 
	PubTopdoc("wcp-pubtopdoc"),
	 
	StatGoodUsers("wcp-statgoodusers"),
	 
	StatGoodGroups("wcp-statgoodgroups"),
	 
	StatMostUsers("wcp-statmostusers"),
	 
	StatGoodDocs("wcp-statgooddocs"),
	 
	StatBadDocs("wcp-statbaddocs"),
	 
	StatNumForDay("wcp-statnumforday"),
	 
	StatUser("wcp-statuser"),
	 
	HotDocGroups("wcp-hotdocgroups");
	
	 
	private String permanentCacheName;
	 
	private String liveCacheName;

	FarmCacheNames(String permanentCacheName) {
		this.permanentCacheName = permanentCacheName;
		this.liveCacheName = permanentCacheName + "-live";
	}

	 
	public String getPermanentCacheName() {
		return permanentCacheName;
	}

	 
	public String getLiveCacheName() {
		return liveCacheName;
	}

}
