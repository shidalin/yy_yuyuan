/**
多表更新，去除计量单位名称前后空格
**/
update bd_measdoc t1
   set name =
       (select ltrim(rtrim(t2.name))
          from bd_measdoc t2
         where t2.code = t1.code)
 where exists (select 1 from bd_measdoc t2 where t2.code = t1.code)
