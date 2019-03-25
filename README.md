# 技术介绍

## 项目简介

乐购商城，基于SOA架构的分布式购物商城,完整的购物流程、后端运营平台对前端业务的支撑，和对项目的运维，有各项的监控指标和运维指标。

本项目由8个后端项目和1个前端项目共同组成。真正的实现了前后端完全分离,实现了异常和日志的统一管理，实现了MQ落地保证100%到达的解决方案。

## **项目架构图**

![项目架构图](http://po4w4fx60.bkt.clouddn.com/lg%E6%9E%B6%E6%9E%84%E5%9B%BE.png)

1.  安全认证：控制层通过spring security进行请求认证  服务端通过实现dubbo过滤器 实现黑名单白名单
2. 单点登录：基于Srping security  Cas  提供(网站前台)单点登录功能。
3.  操作日志：采用Lombok注解方式, 松耦合任意搭配业务异常, 一个注解无需所属性,即可实现用户全纬度的操作日志。
4. 服务限流： redis分布式限流  谷歌的RateLimiter单机限流(令牌桶算法)
5. 消息总线：配置动态实时刷新
6. 数据权限: 采用Mybatis-plus弱化了数据库操作, 采用PageHelper前后端封装了代码,让分页变得更容易,同时也抽取了对应的代码,简单易用。
7. 文件系统: 主要对七牛云提供支持,通过自定义配置,轻松实现私有连接访问和共有连接访问的切换,提供了http 和rpc 两套统一上传接口API
8. 消息中心：聚合数据短信服务接口统一接入、自定义邮件模板、统一消息接口对所有消息模式统一封装
9. 异常报警: 异常统一上抛,采用自定义注解方式进行统一的异常报警,调用lg-task服务将异常消息通知开发者
10. 可靠消息: 基于Rabbit的高性能MQ的可靠消息落地, 提供消息ACK, 开发者无需关注实现,开箱即用,让你的MQ消息可以100%的可达
11. 任务中心: quartz, 基于注解的配置方式,简化开发, 拥有任务状态, 达到任务可溯
12. 框架前端: 基于Angular.js全家桶, 进行页面渲染
13. 支付方案: 未实现
14. 部署方案: jar包部署

## 项目部分核心代码展示

```java
@RestController
@RequestMapping(value = "/brands", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - ProductBrandController", tags = "商品品牌API", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbBrandController extends BaseController {

    @Autowired
    private TbBrandService tbBrandService;


    @GetMapping("")
    @ApiOperation(httpMethod = "GET", value = "获取商品品牌列表")
    public Wrapper<List<TbBrand>> list() {
        logger.info("获取商品品牌列表");
        List<TbBrand> tbBrands = this.tbBrandService.selectList(new QueryWrapper<>());
        return WrapMapper.ok(tbBrands);
    }

    @GetMapping("/")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数", paramType = "query"),
            @ApiImplicitParam(value = "行数", paramType = "query")
    })
    @ApiOperation(httpMethod = "GET", value = "获取商品品牌列表分页")
    public Wrapper<PageVO<TbBrand>> listByPage(Integer page,
                                               Integer rows) {
        logger.info("品牌列表分页,pageNum={},pageSize={}", page, rows);
        PageVO<TbBrand> brandPageVO = this.tbBrandService.findByPage(page, rows, new BrandVO());
        return WrapMapper.ok(brandPageVO);
    }

    @PostMapping("/")
    @ApiOperation(httpMethod = "POST", value = "添加商品品牌")
    public Wrapper save(@RequestBody @ApiParam("商品品牌") TbBrand tbBrand) {
        logger.info("添加商品品牌,{}", tbBrand);
        this.tbBrandService.save(tbBrand);
        return WrapMapper.ok();
    }

    @PostMapping("/search")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数", paramType = "query"),
            @ApiImplicitParam(value = "行数", paramType = "query")
    })
    @ApiOperation(httpMethod = "POST", value = "商品品牌列表搜索")
    public Wrapper<PageVO<TbBrand>> search(Integer page,
                                           Integer rows,
                                           @RequestBody @ApiParam(name = "brandVO", value = "条件") BrandVO brandVO) {
        logger.info("商品品牌列表搜索,pageNum={},pageSize={},brandVO={}", page, rows, brandVO);
        PageVO<TbBrand> brandPageVO = this.tbBrandService.findByPage(page, rows, brandVO);
        return WrapMapper.ok(brandPageVO);
    }

    @GetMapping("/{id}")
    @ApiOperation(httpMethod = "GET", value = "根据品牌ID查询商品品牌")
    public Wrapper<TbBrand> findById(@ApiParam @PathVariable Integer id) {
        logger.info("根据品牌ID查询品牌,ID={}", id);
        TbBrand tbBrand = this.tbBrandService.findById(id);
        return WrapMapper.ok(tbBrand);
    }

    @PutMapping("/")
    @ApiOperation(httpMethod = "PUT", value = "更新商品品牌")
    public Wrapper update(@ApiParam("修改参数") @RequestBody TbBrand brand) {
        logger.info("更新商品品牌,brand={}", brand);
        this.tbBrandService.updateBrand(brand);
        return WrapMapper.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(httpMethod = "DELETE", value = "删除商品品牌")
    public Wrapper del(@ApiParam @PathVariable("id") Long[] ids) {
        logger.info("删除商品品牌,ID={}", Arrays.toString(ids));
        this.tbBrandService.delByIds(ids);
        return WrapMapper.ok();
    }

    @GetMapping("/optionList")
    @ApiOperation(httpMethod = "GET", value = "获取商品类型模板中关联品牌下拉框数据")
    public Wrapper<List<Map>> optionList() {
        logger.info("获取商品类型模板中关联品牌下拉框数据");
        return WrapMapper.ok(this.tbBrandService.findOptionList());
    }
}

```

```java
/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbBrandServiceImpl extends ServiceImpl<TbBrandMapper, TbBrand> implements TbBrandService {


    @Autowired
    private Validator validator;

    /**
     * 查询品牌列表
     * @param queryWrapper
     * @return
     */
    @Override
    public Wrapper<List<TbBrand>>  list(QueryWrapper<TbBrand> queryWrapper) {
        List<TbBrand> list = baseMapper.selectList(queryWrapper);
        return WrapMapper.ok(list);
    }

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @Override
    public Wrapper save(TbBrand brand) {
       BeanValidators.validateWithException(validator, brand, Insert.class);
       Integer num = this.baseMapper.insert(brand);
       if (num != 1){
           throw new BusinessException(ErrorCodeEnum.GL99990500, "添加商品品牌信息失败");
       }
        return WrapMapper.ok();
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Wrapper< PageVO<TbBrand>> listPaVo(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0) {
            pageNum = TbBrand.PAGE_NUM;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = TbBrand.PAGE_SIZE;
        }
        //PageHelper.startPage(pageNum, pageSize);
        IPage<TbBrand> iPage = this.baseMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<TbBrand>());
        PageVO<TbBrand> pageVO = new PageVO<>();
        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);
    }


    /**
     *  按条件 分页 查询品牌
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Wrapper<PageVO<TbBrand>> findPage(Integer pageNum, Integer pageSize, BrandVo brandVo) {
        if (pageNum == null || pageNum == 0) {
            pageNum = TbBrand.PAGE_NUM;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = TbBrand.PAGE_SIZE;
        }
        if(brandVo==null){
            return listPaVo(pageNum,pageSize);
        }
        IPage<TbBrand> brandIPage = this.baseMapper.selectPage(
                new Page<>(pageNum, pageSize),
                new QueryWrapper<TbBrand>().like(StrUtil.isNotBlank(brandVo.getName()), "name",brandVo.getName()).
                        eq(StrUtil.isNotBlank(brandVo.getFirstChar()),
                        "first_char",brandVo.getFirstChar()));
        PageVO<TbBrand> pageVO = new PageVO<>();
        pageVO.setRows(brandIPage.getRecords());
        pageVO.setTotal(brandIPage.getTotal());
        return WrapMapper.ok(pageVO);
}

    /**
     * 删除品牌
     * @param ids
     * @return
     */
    @Override
    public Wrapper delByBrand(Long[] ids) {
        checkNotNull(ids);
        Integer index = this.baseMapper.deleteBatchIds(Arrays.asList(ids));
        if (index != ids.length) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除商品品牌信息失败");
        }
        return WrapMapper.ok();
    }

    /**
     * 按编号查询品牌
     * @param id
     * @return
     */
    @Override
    public Wrapper<TbBrand>  findById(Integer id) {
        checkNotNull(id);
        TbBrand tbBrand = this.baseMapper.selectById(id);
        if (null == tbBrand) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该商品品牌信息");
        }
        return WrapMapper.ok(tbBrand);
    }

    /**
     * 修改品牌信息
     * @param tbBrand
     * @return
     */
    @Override
    public Wrapper updateTbBrand(TbBrand tbBrand) {
        BeanValidators.validateWithException(validator, tbBrand, Update.class);
        Integer index = this.baseMapper.updateById(tbBrand);
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "更新商品品牌信息失败");
        }
        return WrapMapper.ok();
    }

    @Override
    public List<Map> selectOptionList() {
        return this.baseMapper.selectOptionList();
    }

}

```

```java
@Order(100)
@Component
@Aspect
@Slf4j
public class ServiceExceptionHandle {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${project.leader.email}")
    private String projectLeaderEmail;

    @Pointcut(value = "execution(public * com.lg.product.service.impl..*.*(..))")
    private void servicePointcut() {
    }

    /**
     * 任何持有@Transactional注解的方法
     */
    @Pointcut(value = "@annotation(org.springframework.transaction.annotation.Transactional)")
    private void transactionalPointcut() {

    }

    /**
     * 异常处理切面
     * 将异常包装为Response，避免dubbo进行包装
     *
     * @param pjp 处理点
     * @return Object
     */
    @Around("servicePointcut() && !transactionalPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        try {
            return pjp.proceed();
        } catch (BusinessException | ConstraintViolationException e) { // 业务自定义异常
            processException(pjp, args, e,false);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            processException(pjp, args, e,true);
            return WrapMapper.error("服务调用失败");
        } catch (Throwable throwable) {
            processException(pjp, args, throwable,true);
            return WrapMapper.error("系统异常");
        }
    }


    /**
     * 处理异常
     *
     * @param joinPoint 切点
     * @param args      参数
     * @param throwable 异常
     */
    private void processException(final ProceedingJoinPoint joinPoint, final Object[] args, Throwable throwable,
                                  boolean isHandle) {
        String inputParam = "";
        if (args != null && args.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (Object arg : args) {
                sb.append(",");
                sb.append(arg);
            }
            inputParam = sb.toString().substring(1);
        }
        String errorMessage = Throwables.getStackTraceAsString(throwable);
        String method = joinPoint.toLongString();
        if(isHandle){
            ExceptionMsg exceptionMsg = new ExceptionMsg();
            exceptionMsg.setCreateTime(LocalDateTime.now());
            exceptionMsg.setExceptionMethod(method);
            exceptionMsg.setParam(inputParam);
            exceptionMsg.setErrorMessage(errorMessage);
            exceptionMsg.setEmail(projectLeaderEmail);
            this.amqpTemplate.convertAndSend("exceptionMsg", JSON.toJSON(exceptionMsg).toString());
        }
        log.warn("\n 方法: {}\n 入参: {} \n 错误信息: {}", method, inputParam,
                errorMessage);
    }
}
```



## CAS单点登录和第三方登录

### 一. Cas Server 

CAS Server负责完成对用户的认证工作, 需要独立部署, CAS Server 会处理用户名 / 密码等凭证 (Credentials)。

### 二. Cas Client

负责处理对客户端受保护资源的访问请求，需要对请求方进行身份认证时，重定向到CAS Server进行认证。（原则上，客户端应用不再接受任何的用户名密码等 Credentials）。

CAS Client 与受保护的客户端应用部署在一起，以 Filter 方式保护受保护的资源。

### 三. 架构图

![cas架构图](http://www.coin163.com/java/cas/images/cas_clip_image001.jpg)

### 四. Oauth2

- 授权码模式

  ![授权码模式架构图](https://segmentfault.com/img/bVSn2t?w=715&h=414)

- 客户端模式

- 密码模式

- 简化模式



## 基于可靠消息服务的分布式事务

### 一.  生产端的可靠性投递

```
1. 保障消息的成功发出

2. 保障MQ节点的成功接收

3. 发送端收到MQ节点（broker）确认应答

4. 完善的消息补偿机制
```

在实际生产中，很难保障前三点的完全可靠，比如在极端的环境中，生产者发送消息失败了，发送端在接受确认应答时突然发生网络闪断等等情况，很难保障可靠性投递，所以就需要有第四点完善的消息补偿机制。

### 二. 消息落库，对消息状态进行打标

![消息落库的流程图](https://user-gold-cdn.xitu.io/2019/1/24/1687e307e443d151?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

### 三. 代码实现

![1552754936928](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\1552754936928.png)

![1552755041754](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\1552755041754.png)

![1552755127831](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\1552755127831.png)

![1552755221629](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\1552755221629.png)

## 秒杀高并发解决方案

### 一. 秒杀系统的场景特点

- 秒杀时大量用户会在同一时间同时进行抢购，网站瞬时访问流量激增；
- 秒杀一般是访问请求量远远大于库存数量，只有少部分用户能够秒杀成功；
- 秒杀业务流程比较简单，一般就是下订单操作。

### 二. 秒杀架构设计理念

- 限流：鉴于只有少部分用户能够秒杀成功，所以要限制大部分流量，只允许少部分流量进入服务后端（暂未处理）。
- 削峰：对于秒杀系统瞬时的大量用户涌入，所以在抢购开始会有很高的瞬时峰值。实现削峰的常用方法有利用缓存或消息中间件等技术。
- 异步处理：对于高并发系统，采用异步处理模式可以极大地提高系统并发量，异步处理就是削峰的一种实现方式。
- 内存缓存：秒杀系统最大的瓶颈最终都可能会是数据库的读写，主要体现在的磁盘的 I/O，性能会很低，如果能把大部分的业务逻辑都搬到缓存来处理，效率会有极大的提升。
- 可拓展：如果需要支持更多的用户或更大的并发，将系统设计为弹性可拓展的，如果流量来了，拓展机器就好。

![秒杀架构图](http://po4w4fx60.bkt.clouddn.com/%E7%A7%92%E6%9D%80%E6%B5%81%E7%A8%8B%E5%9B%BE.png)

### 三. 代码实现

![1552758620074](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\1552758620074.png)

![1552758662353](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\1552758662353.png)

![1552759295860](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\1552759295860.png)

![1552760689643](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\1552760689643.png)

### 四. 秒杀极端场景分析

​	

| 极端场景                                                     | 解决方案                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| 一个得到锁的线程在执行任务的过程中挂掉，来不及显式地释放锁，这块资源将会永远被锁住，别的线程再也别想进来。 | 设置超时时间,但是像平常一样先通过setnx设置KEY在设置超时时间可能当你设置key后系统突然挂掉并没来得及设置超时时间所有在Redis 2.6.12我们可以同过setnx直接设置超时时间 |
| **del 导致误删**                                             | 使用分布式ID作为value并且在释放锁资源的时候判断值是否相同    |
| 在超时时间内还未完成任务                                     | 使用守护线程当时间快要过期的时候给他续上时间                 |

