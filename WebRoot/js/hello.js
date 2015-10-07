


 

/*
function load()//载入的时候调用
{
    var jbutton = document.getElementById("jbutton");
    jbutton.onclick=function(event)//注册按钮点击事件
    {
        jbClick();
    };
}
*/
function jbClick()//按钮点击事件
{
  //  jshello.getHelloWorld(callback);//没有参数传递，只传递回调函数就行
 //   jsmethodname.alertStr(callback1);//无参数
    var data="js传入java中的值";
  //  jsmethodname.alertJSStr(data,callback);//有参数。第一个为参数，第二个为回调方法接收返回值
  jsmethodname.getList(callbackList);  //无参数,返回list
  //jsmethodname.getObj(callbackObj);
}
function callback1(msg)//回调函数 ，调用java方法的javascript函数
{
     //msg就是java方法的返回值
     alert(msg);
}

function callback(msg)//回调函数
{
    //这里可以进行参数处理DWRUtil 的 setValue() 方法会将传回的 msg 设定给指定 id 的 DOM
    DWRUtil.setValue('jdiv',msg);
}
function callbackList(data){

for(var i=0;i<data.length;i++){
  DWRUtil.setValue("uid", data[i].id);
  DWRUtil.setValue("uname", data[i].name);
  DWRUtil.setValue("usex", data[i].sex);
  DWRUtil.setValue("uaddress", data[i].address);
}
}


function callbackObj(data){
 
 //知道属性
  DWRUtil.setValue("uid", data.id);
  DWRUtil.setValue("uname", data.name);
  DWRUtil.setValue("usex", data.sex);
  DWRUtil.setValue("uaddress", data.address);
 
 /**
 * 不知道属性
for(var property in data){
　　//alert("property:"+property);
　　alert(property+":"+data[property]);
　　}
 */

}

