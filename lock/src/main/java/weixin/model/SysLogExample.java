package weixin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLoginidIsNull() {
            addCriterion("loginid is null");
            return (Criteria) this;
        }

        public Criteria andLoginidIsNotNull() {
            addCriterion("loginid is not null");
            return (Criteria) this;
        }

        public Criteria andLoginidEqualTo(String value) {
            addCriterion("loginid =", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidNotEqualTo(String value) {
            addCriterion("loginid <>", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidGreaterThan(String value) {
            addCriterion("loginid >", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidGreaterThanOrEqualTo(String value) {
            addCriterion("loginid >=", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidLessThan(String value) {
            addCriterion("loginid <", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidLessThanOrEqualTo(String value) {
            addCriterion("loginid <=", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidLike(String value) {
            addCriterion("loginid like", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidNotLike(String value) {
            addCriterion("loginid not like", value, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidIn(List<String> values) {
            addCriterion("loginid in", values, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidNotIn(List<String> values) {
            addCriterion("loginid not in", values, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidBetween(String value1, String value2) {
            addCriterion("loginid between", value1, value2, "loginid");
            return (Criteria) this;
        }

        public Criteria andLoginidNotBetween(String value1, String value2) {
            addCriterion("loginid not between", value1, value2, "loginid");
            return (Criteria) this;
        }

        public Criteria andCorpidIsNull() {
            addCriterion("corpid is null");
            return (Criteria) this;
        }

        public Criteria andCorpidIsNotNull() {
            addCriterion("corpid is not null");
            return (Criteria) this;
        }

        public Criteria andCorpidEqualTo(String value) {
            addCriterion("corpid =", value, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidNotEqualTo(String value) {
            addCriterion("corpid <>", value, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidGreaterThan(String value) {
            addCriterion("corpid >", value, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidGreaterThanOrEqualTo(String value) {
            addCriterion("corpid >=", value, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidLessThan(String value) {
            addCriterion("corpid <", value, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidLessThanOrEqualTo(String value) {
            addCriterion("corpid <=", value, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidLike(String value) {
            addCriterion("corpid like", value, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidNotLike(String value) {
            addCriterion("corpid not like", value, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidIn(List<String> values) {
            addCriterion("corpid in", values, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidNotIn(List<String> values) {
            addCriterion("corpid not in", values, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidBetween(String value1, String value2) {
            addCriterion("corpid between", value1, value2, "corpid");
            return (Criteria) this;
        }

        public Criteria andCorpidNotBetween(String value1, String value2) {
            addCriterion("corpid not between", value1, value2, "corpid");
            return (Criteria) this;
        }

        public Criteria andMethodtypeIsNull() {
            addCriterion("methodtype is null");
            return (Criteria) this;
        }

        public Criteria andMethodtypeIsNotNull() {
            addCriterion("methodtype is not null");
            return (Criteria) this;
        }

        public Criteria andMethodtypeEqualTo(String value) {
            addCriterion("methodtype =", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeNotEqualTo(String value) {
            addCriterion("methodtype <>", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeGreaterThan(String value) {
            addCriterion("methodtype >", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeGreaterThanOrEqualTo(String value) {
            addCriterion("methodtype >=", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeLessThan(String value) {
            addCriterion("methodtype <", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeLessThanOrEqualTo(String value) {
            addCriterion("methodtype <=", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeLike(String value) {
            addCriterion("methodtype like", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeNotLike(String value) {
            addCriterion("methodtype not like", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeIn(List<String> values) {
            addCriterion("methodtype in", values, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeNotIn(List<String> values) {
            addCriterion("methodtype not in", values, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeBetween(String value1, String value2) {
            addCriterion("methodtype between", value1, value2, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeNotBetween(String value1, String value2) {
            addCriterion("methodtype not between", value1, value2, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodnameIsNull() {
            addCriterion("methodname is null");
            return (Criteria) this;
        }

        public Criteria andMethodnameIsNotNull() {
            addCriterion("methodname is not null");
            return (Criteria) this;
        }

        public Criteria andMethodnameEqualTo(String value) {
            addCriterion("methodname =", value, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameNotEqualTo(String value) {
            addCriterion("methodname <>", value, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameGreaterThan(String value) {
            addCriterion("methodname >", value, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameGreaterThanOrEqualTo(String value) {
            addCriterion("methodname >=", value, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameLessThan(String value) {
            addCriterion("methodname <", value, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameLessThanOrEqualTo(String value) {
            addCriterion("methodname <=", value, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameLike(String value) {
            addCriterion("methodname like", value, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameNotLike(String value) {
            addCriterion("methodname not like", value, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameIn(List<String> values) {
            addCriterion("methodname in", values, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameNotIn(List<String> values) {
            addCriterion("methodname not in", values, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameBetween(String value1, String value2) {
            addCriterion("methodname between", value1, value2, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodnameNotBetween(String value1, String value2) {
            addCriterion("methodname not between", value1, value2, "methodname");
            return (Criteria) this;
        }

        public Criteria andMethodjcIsNull() {
            addCriterion("methodjc is null");
            return (Criteria) this;
        }

        public Criteria andMethodjcIsNotNull() {
            addCriterion("methodjc is not null");
            return (Criteria) this;
        }

        public Criteria andMethodjcEqualTo(String value) {
            addCriterion("methodjc =", value, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcNotEqualTo(String value) {
            addCriterion("methodjc <>", value, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcGreaterThan(String value) {
            addCriterion("methodjc >", value, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcGreaterThanOrEqualTo(String value) {
            addCriterion("methodjc >=", value, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcLessThan(String value) {
            addCriterion("methodjc <", value, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcLessThanOrEqualTo(String value) {
            addCriterion("methodjc <=", value, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcLike(String value) {
            addCriterion("methodjc like", value, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcNotLike(String value) {
            addCriterion("methodjc not like", value, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcIn(List<String> values) {
            addCriterion("methodjc in", values, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcNotIn(List<String> values) {
            addCriterion("methodjc not in", values, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcBetween(String value1, String value2) {
            addCriterion("methodjc between", value1, value2, "methodjc");
            return (Criteria) this;
        }

        public Criteria andMethodjcNotBetween(String value1, String value2) {
            addCriterion("methodjc not between", value1, value2, "methodjc");
            return (Criteria) this;
        }

        public Criteria andSccessIsNull() {
            addCriterion("sccess is null");
            return (Criteria) this;
        }

        public Criteria andSccessIsNotNull() {
            addCriterion("sccess is not null");
            return (Criteria) this;
        }

        public Criteria andSccessEqualTo(Boolean value) {
            addCriterion("sccess =", value, "sccess");
            return (Criteria) this;
        }

        public Criteria andSccessNotEqualTo(Boolean value) {
            addCriterion("sccess <>", value, "sccess");
            return (Criteria) this;
        }

        public Criteria andSccessGreaterThan(Boolean value) {
            addCriterion("sccess >", value, "sccess");
            return (Criteria) this;
        }

        public Criteria andSccessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("sccess >=", value, "sccess");
            return (Criteria) this;
        }

        public Criteria andSccessLessThan(Boolean value) {
            addCriterion("sccess <", value, "sccess");
            return (Criteria) this;
        }

        public Criteria andSccessLessThanOrEqualTo(Boolean value) {
            addCriterion("sccess <=", value, "sccess");
            return (Criteria) this;
        }

        public Criteria andSccessIn(List<Boolean> values) {
            addCriterion("sccess in", values, "sccess");
            return (Criteria) this;
        }

        public Criteria andSccessNotIn(List<Boolean> values) {
            addCriterion("sccess not in", values, "sccess");
            return (Criteria) this;
        }

        public Criteria andSccessBetween(Boolean value1, Boolean value2) {
            addCriterion("sccess between", value1, value2, "sccess");
            return (Criteria) this;
        }

        public Criteria andSccessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("sccess not between", value1, value2, "sccess");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("result like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("result not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNull() {
            addCriterion("createdate is null");
            return (Criteria) this;
        }

        public Criteria andCreatedateIsNotNull() {
            addCriterion("createdate is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedateEqualTo(Date value) {
            addCriterion("createdate =", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotEqualTo(Date value) {
            addCriterion("createdate <>", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThan(Date value) {
            addCriterion("createdate >", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("createdate >=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThan(Date value) {
            addCriterion("createdate <", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateLessThanOrEqualTo(Date value) {
            addCriterion("createdate <=", value, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateIn(List<Date> values) {
            addCriterion("createdate in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotIn(List<Date> values) {
            addCriterion("createdate not in", values, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateBetween(Date value1, Date value2) {
            addCriterion("createdate between", value1, value2, "createdate");
            return (Criteria) this;
        }

        public Criteria andCreatedateNotBetween(Date value1, Date value2) {
            addCriterion("createdate not between", value1, value2, "createdate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}