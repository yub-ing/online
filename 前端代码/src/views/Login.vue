<template>

  <!--  登录-->
  <div class="fullscreen-warp">
    <div class="login-bg"></div>
    <div class="login-logo">
		<span>
			<p style="width: 900px;margin-top: 0;padding: 0;text-align: center">{{ appConfig.name }}</p>
		</span>
    </div>
    <div class="login-content">
      <form id="loginForm">
        <el-input v-model="username" type="text" class="login-username" placeholder="请输入用户名"></el-input>
        <el-input v-model="password" type="password" class="login-password" placeholder="请输入密码"></el-input>
        <el-select filterable v-model="usertype" class="login-usertype" clearable>
          <el-option  v-for="item in conf.usertypeList" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
        <el-input v-model="captcha" type="text" class="login-captcha" maxlength="4"></el-input>
        <el-image class="login-captcha-img" :src="codeImg" alt="验证码" @click="getCode"></el-image>
        <div class="login-setting">
        </div>
        <div class="longin-submitbtn" @click="login" >登录</div>
        <span class="registerBtn" @click="registerShow=true" >没有账号？去注册</span>
      </form>
    </div>
    <div class="login-footer">
      <p>{{ appConfig.copyRight }}</p>
    </div>
  </div>

  <!--  注册-->
  <el-dialog v-model="registerShow" title="注册" width="500px" align-center center>

    <div style="padding-top: 30px;padding-right: 30px">
      <el-form label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="username" type="text" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="password2" type="password" placeholder="确认密码"></el-input>
        </el-form-item>
        <el-form-item label="用户类型">
          <el-select filterable v-model="usertype" clearable>
            <el-option  v-for="item in conf.usertypeList" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
      </el-form>
    </div>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="registerShow = false">取消</el-button>
        <el-button type="primary" @click="register">立即注册</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup>
  import {GlobalConfig, useStore} from 'core';
  const {appConfig} = GlobalConfig();
  import CusBase64 from '@/utils/base64'
  import {onMounted} from "vue";
  const router = useRouter();
  let conf = codeying
  let username = ref("");//登录注册公用
  let password = ref("");//登录注册公用
  let password2 = ref("");
  let usertype = ref("");//登录注册公用
  let captcha = ref(null)
  let codeImg = ref(null)
  let captchaKey = ''
  let registerShow = ref(false)//注册页面显示

  const getCode = async ()=> {
    const {data} = await Http.get("/captcha")

    if(Helper.empty(data)) {
      Msg.error('验证码获取失败')
      codeImg.value = ''
    }
    captchaKey = data.captchaKey
    codeImg.value = 'data:image/png;base64,'+data.captcha
  }
  onMounted(()=>{
    //角色默认选中第一个
    usertype.value = conf.usertypeList[0].value
    Cache.remove('token')
    getCode()
  })


  const login = async () => {

    // const pwd = CusBase64.encoder(password.value)
    //登录参数
    let data = {
      username: username.value,
      password: password.value,
      usertype: usertype.value,
      captcha: captcha.value,
      captchaKey,
    }

    Msg.loading('登录中')
    let resp = await Http.get("/login", data)
    Msg.loading(false)
    if(resp.code == 200) {
      Msg.success('登录成功');
      let responseData = resp.data;
      //登录用户信息缓存
      console.log(responseData.user)
      Cache.setUser(responseData.user)
      //设置token
      Cache.setToken(responseData.token);
      //登录成功，跳转首页
      router.push({path: appConfig.home});
    }else {
      getCode()
    }
  }
  const register = async () => {
    if(password.value!==password2.value){
      Msg.error("密码不一致!");
      return
    }
    // const pwd = CusBase64.encoder(password.value)
    //注册参数
    let data = {
      username: username.value,
      password: password.value,
      usertype: usertype.value,
    }

    Msg.loading('注册中')
    let resp = await Http.post("/register", data)
    Msg.loading(false)
    if(resp.code == 200) {
      Msg.success('注册成功，请登录');
      registerShow.value = false;
    }else {
      Msg.success('注册失败');
    }
  }
</script>

<style scoped lang="less">
  .fullscreen-warp{
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
  }
  .login-bg{
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
  }
  .login-logo{
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -270px;
    margin-left: -450px;
    width: 900px;
    height: 55px;
  }

  .login-own-logo{
    position: absolute;
    top: 38%;
    left: 50%;
    margin-top: -220px;
    margin-left: -199px;
    width: 410px;
    height: 55px;
  }

  .login-content{
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -250px;
    margin-left: -450px;
    width: 900px;
    height: 440px;
  }

  .login-username,.login-usertype,.login-password,.login-captcha,.login-captcha-img{
    position: absolute!important;
    left: 320px!important;
    width: 285px!important;
    height: 30px!important;
    font: normal 14px/30px "微软雅黑"!important;
    border: none!important;

  }
  #loginForm /deep/ .el-input__wrapper {
    box-shadow: none!important;
  }

  .login-username{
    top: 145px!important;
  }

  .login-password{
    top: 196px!important;
  }
  .login-usertype{
    top: 249px!important;
  }

  .login-captcha{
    top: 300px!important;
    width: 200px!important;
    border: 1px solid #B1D0EB!important;
    border-radius: 3px!important;
    box-shadow: #B1D0EB 0px 0px 2px 0px!important;
  }

  .login-captcha-img	{
    top: 300px!important;
    width: 75px!important;
    left: 530px!important;
  }

  .login-setting{
    position: absolute!important;
    top: 325px!important;
    left: 280px!important;
    width: 378px!important;
    height: 16px!important;
    font: normal 14px/16px "微软雅黑"!important;
    color: #2b89ce!important;
    text-align: left!important;
  }

  .login-setting a{
    color: #2b89ce!important;
  }

  .longin-submitbtn{
    position: absolute!important;
    top: 340px!important;
    left: 370px!important;
    width: 136px!important;
    height: 30px!important;
    font: normal 16px/30px "微软雅黑"!important;
    color: #FFF!important;
    background-color: #FF7B2C;
    border-radius: 3px!important;
    cursor: pointer!important;
    text-align: center!important;
  }
  .registerBtn{
    position: absolute!important;
    top: 350px!important;
    left: 512px!important;
    width: 136px!important;
    height: 30px!important;
    font: normal 10px "微软雅黑"!important;
    color: #1717f1 !important;
    cursor: pointer!important;
  }

  .login-footer{
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: 230px;
    margin-left: -450px;
    width: 900px;
    text-align: center;
    line-height: 30px;
    color: #EEE;
    font-size: 12px;
  }
</style>
