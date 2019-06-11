package com.wwzs.component.commonsdk.core;

/**
 * RouterHub 用来定义路由器的路由地址, 以组件名作为前缀, 对每个组件的路由地址进行分组, 可以统一查看和管理所有分组的路由地址
 * <p>
 * RouterHub 存在于基础库, 可以被看作是所有组件都需要遵守的通讯协议, 里面不仅可以放路由地址常量, 还可以放跨组件传递数据时命名的各种 Key 值
 * 再配以适当注释, 任何组件开发人员不需要事先沟通只要依赖了这个协议, 就知道了各自该怎样协同工作, 既提高了效率又降低了出错风险, 约定的东西自然要比口头上说强
 * <p>
 * 如果您觉得把每个路由地址都写在基础库的 RouterHub 中, 太麻烦了, 也可以在每个组件内部建立一个私有 RouterHub, 将不需要跨组件的
 * 路由地址放入私有 RouterHub 中管理, 只将需要跨组件的路由地址放入基础库的公有 RouterHub 中管理, 如果您不需要集中管理所有路由地址的话
 * 这也是比较推荐的一种方式
 * <p>
 * 路由地址的命名规则为 组件名 + 页面名, 如订单组件的订单详情页的路由地址可以命名为 "/order/OrderDetailActivity"
 * <p>
 * ARouter 将路由地址中第一个 '/' 后面的字符称为 Group, 比如上面的示例路由地址中 order 就是 Group, 以 order 开头的地址都被分配该 Group 下
 * 切记不同的组件中不能出现名称一样的 Group, 否则会发生该 Group 下的部分路由地址找不到的情况!!!
 * 所以每个组件使用自己的组件名作为 Group 是比较好的选择, 毕竟组件不会重名
 */
public interface RouterHub {
    /**
     * 组名
     */
    String APP = "/app";//宿主 App 组件
    String NEWAPP = "/newApp";//更新组建
    /**
     * 服务组件, 用于给每个组件暴露特有的服务
     */
    String SERVICE = "/service";

    /**
     * 宿主 App 分组
     */
    String APP_LIVEREPAIRFRAGMENT = APP + "/LiveRepairFragment"; //现场管理—公区报修

    String APP_TRANSLOOPACTIVITY = APP + "/TransLoopActivity";// 循环运送
    String APP_TRANSMANAGEACTIVITY = APP + "/TransManageActivity";// 中央运送
    String APP_FORUMDETAILACTIVITY = APP + "/ForumDetailActivity";// 帖子详情
    String APP_FORUMLISTACTIVITY = APP + "/ForumListActivity";//帖子列表
    String APP_LIVEAUTHACTIVITY = APP + "/LiveAuthActivity";//员工认证
    String APP_SELECTRECEIVERACTIVITY = APP + "/SelectReceiverActivity";//内部邮件选择人员
    String APP_SECURITYLOGDETAILACTIVITY = APP + "/SecurityLogDetailActivity";//保安记录详情
    String APP_CHECKORDERNEWDETAILACTIVITY = APP + "/CheckOrderNewDetailActivity";//检查工单详情
    String APP_CLEANDETAILACTIVITY = APP + "/CleanDetailActivity";//保洁工作提交
    String APP_CLEANLOGDETAILACTIVITY = APP + "/CleanLogDetailActivity";//保洁记录
    String APP_REPAIRORDERDETAILACTIVITY = APP + "/RepairOrderDetailActivity";//报修详情
    String APP_REPAIRSTATISACTIVITY = APP + "/RepairStatisActivity";//维修工单统计
    String APP_DEVICERECORDDETAILACTIVITY = APP + "/DeviceRecordDetailActivity";//设备档案详情
    String APP_CHECKDATADETAILACTIVITY = APP + "/CheckDataDetailActivity";//工作查询详情
    String APP_SECURITYDETAILACTIVITY = APP + "/SecurityDetailActivity";//保安执勤详情
    String APP_MAINLOGDETAILACTIVITY = APP + "/MainLogDetailActivity";//保养记录详情
    String APP_MAINORDERDETAILACTIVITY = APP + "/MainOrderDetailActivity";//保养工单详情
    String APP_E6_MYNOTEACTIVITY = APP + "/E6_MyNoteActivity";//我的发帖
    String APP_FORUMPOSTACTIVITY = APP + "/ForumPostActivity";//发帖
    String APP_POWERLOGFRAGMENT = APP + "/PowerLogFragment";//抄表记录
    String APP_LIVERECORDACTIVITY = APP + "/LiveRecordActivity";//报修记录
    String APP_INDUSTRYNEWSACTIVITY = APP + "/IndustryNewsActivity";//行业动态
    String APP_FIRMNEWSACTIVITY = APP + "/FirmNewsActivity";//企业动态
    String APP_POLICYACTIVITY = APP + "/PolicyActivity";//政策法规
    String APP_INNERCONTACTACTIVITY = APP + "/InnerContactActivity";//通讯录
    String APP_REGULARACTIVITY = APP + "/RegularActivity";//规章制度
    String APP_LIVEAUTHINFOACTIVITY = APP + "/LiveAuthInfoActivity";//身份认证
    String APP_G0_SETTINGACTIVITY = APP + "/G0_SettingActivity";//设置中心
    String APP_WRITEEMAILACTIVITY = APP + "/WriteEmailActivity";//写邮件
    String APP_EMAILDETAILACTIVITY = APP + "/EmailDetailActivity";//邮件详情
    String APP_REPAIRORDERHOMEACTIVITY = APP + "/RepairOrderHomeActivity";//维修记录
    String APP_DEVICEMAINTAINACTIVITY = APP + "/DeviceMaintainActivity";//设备保养
    String APP_CLEANACTIVITY = APP + "/CleanActivity";//保洁工作
    String APP_SECURITYACTIVITY = APP + "/SecurityActivity";//保安执勤
    String APP_POWERRECORDACTIVITY = APP + "/PowerRecordActivity";//能源抄表
    String APP_CAPTUREACTIVITY = APP + "/CaptureActivity";//扫描二维码
    String APP_NEWLYNOTIFYACTIVITY = APP + "/NewlyNotifyActivity";//最新通知和公告
    String APP_PROFESSIONACTIVITY = APP + "/ProfessionActivity";//专业资料
    String APP_EMERGENCYACTIVITY = APP + "/EmergencyActivity";//应急预案
    String APP_CHECKDATAHOMEACTIVITY = APP + "/CheckDataHomeActivity";//数据查询
    String APP_INNEREMAILACTIVITY = APP + "/InnerEmailActivity"; //邮件
    String APP_OFFICEMENUACTIVITY = APP + "/OfficeMenuActivity"; //综合行政
    String APP_WORKDEALHOMEACTIVITY = APP + "/WorkDealHomeActivity"; //审批
    String APP_A0_SIGNINACTIVITY = APP + "/A0_SigninActivity"; //登陆
    String APP_NOTIFYDETAILACTIVITY = APP + "/NotifyDetailActivity"; //通知详情
    String APP_CHECKCHECKDETAILACTIVITY = APP + "/CheckCheckDetailActivity"; //专业检查
    String APP_DOCUMENTDETAILACTIVITY = APP + "/DocumentDetailActivity"; //公文审核
    String APP_PURCHASESELFCHECKACTIVITY = APP + "/PurchaseSelfCheckActivity"; //集采
    String APP_CARAPPLYDETAILACTIVITY = APP + "/CarApplyDetailActivity"; //用车
    String APP_MEETINGDETAILACTIVITY = APP + "/MeetingDetailActivity"; //会议审批
    String APP_SEALCHECKDETAILACTIVITY = APP + "/SealCheckDetailActivity"; //用印审批
    String APP_SUPPLIESDETAILACTIVITY = APP + "/SuppliesDetailActivity"; //物资领用
    String APP_ATTCHECKDETAILACTIVITY = APP + "/AttCheckDetailActivity"; //考勤审批
    String APP_CONTACTDEALDETAILACTIVITY = APP + "/ContactDealDetailActivity"; //工作联系单
    String APP_PURCHASEGROUPCHECKACTIVITY = APP + "/PurchaseGroupCheckActivity"; //项目集采
    String APP_WORKPLANDETAILACTIVITY = APP + "/WorkPlanDetailActivity"; //工作计划
    String APP_DOCUMENTAPPLYFRAGMENT = APP + "/DocumentApplyFragment"; //公文呈报
    String APP_SEALAPPLYFRAGMENT = APP + "/SealApplyFragment"; //用印申请


    /**
     * NewApp
     */
    String NEWAPP_PERSONALFRAGMENT = NEWAPP + "/PersonalFragment";//个人中心
    String NEWAPP_COMMUNITYFRAGMENT = NEWAPP + "/CommunityFragment";//互动交流
    String NEWAPP_MOBILEOFFICEFRAGMENT = NEWAPP + "/MobileOfficeFragment";
    String NEWAPP_LIVEREPAIRRECORDFRAGMENT = NEWAPP + "/LiveRepairRecordFragment"; //维修记录
    String NEWAPP_MANAGEMENTFRAGMENT = NEWAPP + "/ManagementFragment";
    String NEWAPP_SIGNINQUERYACTIVITY = NEWAPP + "/SignInQueryActivity";
    String NEWAPP_CLOCKINACTIVITY = NEWAPP + "/ClockInActivity";
    String NEWAPP_WORKQUERYACTIVITY = NEWAPP + "/WorkQueryActivity"; //工作查询列表
    String NEWAPP_MAINTAINACTIVITY = NEWAPP + "/MaintainActivity"; //设备保养
    String NEWAPP_SELECTITEMACTIVITY = NEWAPP + "/SelectItemActivity"; //选择部门
    String NEWAPP_REVIEWPATHACTIVITY = NEWAPP + "/ReviewPathActivity"; //审核路径
    String NEWAPP_OFFICIALDOCUMENTREPORTEDACTIVITY = NEWAPP + "/OfficialDocumentReportedActivity"; //公文呈报
    String NEWAPP_PRINTAPPLICATIONFORMACTIVITY = NEWAPP + "/PrintApplicationFormActivity"; //用印申请
    String NEWAPP_LISTFRAGMENT = NEWAPP + "/ListFragment"; //列表
    String NEWAPP_SERVICE_NEWAPPINFOSERVICE = NEWAPP + SERVICE + "/MobileOfficeFragment";
    String NEWAPP_EXAMINEACTIVITY = NEWAPP + "/ExamineActivity";//专业检查

}
