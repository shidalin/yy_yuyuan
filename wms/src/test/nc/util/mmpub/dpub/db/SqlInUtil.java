package nc.util.mmpub.dpub.db;

import java.util.ArrayList;
import java.util.List;

import nc.bs.bd.util.DBAUtil;
import nc.bs.dao.BaseDAO;
import nc.impl.pubapp.pattern.database.TempTable;
import nc.jdbc.framework.SQLParameter;
import nc.vo.pub.BusinessException;

/**
 * 创建inSQl.
 * <p>
 * 1.根据传入的值返回inSQL.
 * <p>
 * 2.阈值200,id在200以内直接返回in(id1,id2,....)如果超过200则以临时表形式返回SQL.
 * <p>
 * 3.如果在需要在同一事务中多次使用需要提供自己的临时表名，后续会做相关支持.
 * 
 * @since 6.5
 * @version 2012-8-2 下午03:51:04
 * @author zhaoshb
 */
public class SqlInUtil {

    private final int MAX_COUNT = 200;

    private final String ID_LENGTH = "200";

    private final String TEMP_NAME = "t_mm_general_in";

    private String[] ids = null;

    private BaseDAO baseDAO = null;

    public SqlInUtil(String[] ids) {
        this.ids = ids;
    }

    public String getInSql() throws BusinessException {
        return this.getInSql(this.TEMP_NAME);
    }

    public String getInSql(String tempTableName) throws BusinessException {
        if (this.ids == null || this.ids.length == 0) {
            return null;
        }
        if (this.ids.length < this.MAX_COUNT) {
            return this.getCommonInSql();
        }
        return this.getTempTableInSql(tempTableName);
    }

    private String getCommonInSql() {
        StringBuilder inSql = new StringBuilder();
        inSql.append(" in ( ");
        for (String idTemp : this.getIds()) {
            inSql.append(" '");
            inSql.append(idTemp);
            inSql.append("'  , ");
        }
        String subString = inSql.substring(0, inSql.length() - 3);
        String finalInSql = subString + " ) ";
        return finalInSql;
    }

    private String getTempTableInSql(String tableName) throws BusinessException {
        String rtnTableName = this.createTempTable(tableName);
        this.insertData(rtnTableName);
        StringBuilder inSql = new StringBuilder();
        inSql.append(" in ( select id from ");
        inSql.append(tableName);
        inSql.append(" ) ");
        return inSql.toString();
    }

    private String createTempTable(String tableName) {
        TempTable tempTableUtil = new TempTable();
        String[] columns = this.getColumns();
        String rtnTableName = tempTableUtil.getTempTable(tableName, columns, this.getColuntTypes());
        return rtnTableName;
    }

    private void insertData(String tableName) throws BusinessException {
        String insertSql = this.getInsertSql(tableName);
        List<SQLParameter> paraList = this.getParaList();
        DBAUtil.execBatchSql(insertSql, paraList);
    }

    private String getInsertSql(String tableName) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" insert into ");
        insertSql.append(tableName);
        insertSql.append("( id ) values( ? )");
        return insertSql.toString();
    }

    private List<SQLParameter> getParaList() {
        List<SQLParameter> paraList = new ArrayList<SQLParameter>();
        for (String idTemp : this.getIds()) {
            SQLParameter para = new SQLParameter();
            para.addParam(idTemp);
            paraList.add(para);
        }
        return paraList;
    }

    private String[] getColumns() {
        return new String[] {
            "id"
        };
    }

    private String[] getColuntTypes() {
        return new String[] {
            "VARCHAR(" + this.ID_LENGTH + ")"
        };
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String[] getIds() {
        return this.ids;
    }

    public BaseDAO getBaseDAO() {
        if (this.baseDAO == null) {
            this.baseDAO = new BaseDAO();
        }
        return this.baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

}
