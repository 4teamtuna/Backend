package com.example.gsmoa.Contest.entity;

public enum ContestTag {
    PLAN_IDEA("기획/아이디어"),
    AD_MARKETING("광고/마케팅"),
    ART_MUSIC("예체능/미술/음악"),
    EXTERNAL_ACTIVITY("대외활동/서포터즈"),
    EMPLOYMENT_STARTUP("취업/창업"),
    PAPER_REPORT("논문/리포트"),
    WEB_MOBILE_IT("웹/모바일/IT"),
    DESIGN_CHARACTER_WEBTOON("디자인/캐릭터/웹툰"),
    GAME_SOFTWARE("게임/소프트웨어"),
    SCIENCE_ENGINEERING("과학/공학"),
    ARCHITECTURE_CONSTRUCTION("건축/건설/인테리어"),
    LITERATURE_SCRIPT("문학/글/시나리오"),
    VIDEO_UCC_PHOTO("영상/UCC/사진"),
    VOLUNTEER_ACTIVITY("봉사활동"),
    NAMING_SLOGAN("네이밍/슬로건");

    private String displayName;

    ContestTag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}