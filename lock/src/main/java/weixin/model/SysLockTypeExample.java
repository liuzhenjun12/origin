package weixin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysLockTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysLockTypeExample() {
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

        public Criteria andSbtypeIsNull() {
            addCriterion("sbtype is null");
            return (Criteria) this;
        }

        public Criteria andSbtypeIsNotNull() {
            addCriterion("sbtype is not null");
            return (Criteria) this;
        }

        public Criteria andSbtypeEqualTo(String value) {
            addCriterion("sbtype =", value, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeNotEqualTo(String value) {
            addCriterion("sbtype <>", value, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeGreaterThan(String value) {
            addCriterion("sbtype >", value, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeGreaterThanOrEqualTo(String value) {
            addCriterion("sbtype >=", value, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeLessThan(String value) {
            addCriterion("sbtype <", value, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeLessThanOrEqualTo(String value) {
            addCriterion("sbtype <=", value, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeLike(String value) {
            addCriterion("sbtype like", value, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeNotLike(String value) {
            addCriterion("sbtype not like", value, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeIn(List<String> values) {
            addCriterion("sbtype in", values, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeNotIn(List<String> values) {
            addCriterion("sbtype not in", values, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeBetween(String value1, String value2) {
            addCriterion("sbtype between", value1, value2, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbtypeNotBetween(String value1, String value2) {
            addCriterion("sbtype not between", value1, value2, "sbtype");
            return (Criteria) this;
        }

        public Criteria andSbjcIsNull() {
            addCriterion("sbjc is null");
            return (Criteria) this;
        }

        public Criteria andSbjcIsNotNull() {
            addCriterion("sbjc is not null");
            return (Criteria) this;
        }

        public Criteria andSbjcEqualTo(String value) {
            addCriterion("sbjc =", value, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcNotEqualTo(String value) {
            addCriterion("sbjc <>", value, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcGreaterThan(String value) {
            addCriterion("sbjc >", value, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcGreaterThanOrEqualTo(String value) {
            addCriterion("sbjc >=", value, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcLessThan(String value) {
            addCriterion("sbjc <", value, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcLessThanOrEqualTo(String value) {
            addCriterion("sbjc <=", value, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcLike(String value) {
            addCriterion("sbjc like", value, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcNotLike(String value) {
            addCriterion("sbjc not like", value, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcIn(List<String> values) {
            addCriterion("sbjc in", values, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcNotIn(List<String> values) {
            addCriterion("sbjc not in", values, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcBetween(String value1, String value2) {
            addCriterion("sbjc between", value1, value2, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbjcNotBetween(String value1, String value2) {
            addCriterion("sbjc not between", value1, value2, "sbjc");
            return (Criteria) this;
        }

        public Criteria andSbxlidIsNull() {
            addCriterion("sbxlid is null");
            return (Criteria) this;
        }

        public Criteria andSbxlidIsNotNull() {
            addCriterion("sbxlid is not null");
            return (Criteria) this;
        }

        public Criteria andSbxlidEqualTo(Integer value) {
            addCriterion("sbxlid =", value, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlidNotEqualTo(Integer value) {
            addCriterion("sbxlid <>", value, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlidGreaterThan(Integer value) {
            addCriterion("sbxlid >", value, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlidGreaterThanOrEqualTo(Integer value) {
            addCriterion("sbxlid >=", value, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlidLessThan(Integer value) {
            addCriterion("sbxlid <", value, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlidLessThanOrEqualTo(Integer value) {
            addCriterion("sbxlid <=", value, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlidIn(List<Integer> values) {
            addCriterion("sbxlid in", values, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlidNotIn(List<Integer> values) {
            addCriterion("sbxlid not in", values, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlidBetween(Integer value1, Integer value2) {
            addCriterion("sbxlid between", value1, value2, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlidNotBetween(Integer value1, Integer value2) {
            addCriterion("sbxlid not between", value1, value2, "sbxlid");
            return (Criteria) this;
        }

        public Criteria andSbxlnameIsNull() {
            addCriterion("sbxlname is null");
            return (Criteria) this;
        }

        public Criteria andSbxlnameIsNotNull() {
            addCriterion("sbxlname is not null");
            return (Criteria) this;
        }

        public Criteria andSbxlnameEqualTo(String value) {
            addCriterion("sbxlname =", value, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameNotEqualTo(String value) {
            addCriterion("sbxlname <>", value, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameGreaterThan(String value) {
            addCriterion("sbxlname >", value, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameGreaterThanOrEqualTo(String value) {
            addCriterion("sbxlname >=", value, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameLessThan(String value) {
            addCriterion("sbxlname <", value, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameLessThanOrEqualTo(String value) {
            addCriterion("sbxlname <=", value, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameLike(String value) {
            addCriterion("sbxlname like", value, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameNotLike(String value) {
            addCriterion("sbxlname not like", value, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameIn(List<String> values) {
            addCriterion("sbxlname in", values, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameNotIn(List<String> values) {
            addCriterion("sbxlname not in", values, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameBetween(String value1, String value2) {
            addCriterion("sbxlname between", value1, value2, "sbxlname");
            return (Criteria) this;
        }

        public Criteria andSbxlnameNotBetween(String value1, String value2) {
            addCriterion("sbxlname not between", value1, value2, "sbxlname");
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

        public Criteria andUpdatedateIsNull() {
            addCriterion("updatedate is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedateIsNotNull() {
            addCriterion("updatedate is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedateEqualTo(Date value) {
            addCriterion("updatedate =", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateNotEqualTo(Date value) {
            addCriterion("updatedate <>", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateGreaterThan(Date value) {
            addCriterion("updatedate >", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateGreaterThanOrEqualTo(Date value) {
            addCriterion("updatedate >=", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateLessThan(Date value) {
            addCriterion("updatedate <", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateLessThanOrEqualTo(Date value) {
            addCriterion("updatedate <=", value, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateIn(List<Date> values) {
            addCriterion("updatedate in", values, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateNotIn(List<Date> values) {
            addCriterion("updatedate not in", values, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateBetween(Date value1, Date value2) {
            addCriterion("updatedate between", value1, value2, "updatedate");
            return (Criteria) this;
        }

        public Criteria andUpdatedateNotBetween(Date value1, Date value2) {
            addCriterion("updatedate not between", value1, value2, "updatedate");
            return (Criteria) this;
        }

        public Criteria andCreatebyIsNull() {
            addCriterion("createby is null");
            return (Criteria) this;
        }

        public Criteria andCreatebyIsNotNull() {
            addCriterion("createby is not null");
            return (Criteria) this;
        }

        public Criteria andCreatebyEqualTo(String value) {
            addCriterion("createby =", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotEqualTo(String value) {
            addCriterion("createby <>", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyGreaterThan(String value) {
            addCriterion("createby >", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyGreaterThanOrEqualTo(String value) {
            addCriterion("createby >=", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyLessThan(String value) {
            addCriterion("createby <", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyLessThanOrEqualTo(String value) {
            addCriterion("createby <=", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyLike(String value) {
            addCriterion("createby like", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotLike(String value) {
            addCriterion("createby not like", value, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyIn(List<String> values) {
            addCriterion("createby in", values, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotIn(List<String> values) {
            addCriterion("createby not in", values, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyBetween(String value1, String value2) {
            addCriterion("createby between", value1, value2, "createby");
            return (Criteria) this;
        }

        public Criteria andCreatebyNotBetween(String value1, String value2) {
            addCriterion("createby not between", value1, value2, "createby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIsNull() {
            addCriterion("updateby is null");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIsNotNull() {
            addCriterion("updateby is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatebyEqualTo(String value) {
            addCriterion("updateby =", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotEqualTo(String value) {
            addCriterion("updateby <>", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyGreaterThan(String value) {
            addCriterion("updateby >", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyGreaterThanOrEqualTo(String value) {
            addCriterion("updateby >=", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLessThan(String value) {
            addCriterion("updateby <", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLessThanOrEqualTo(String value) {
            addCriterion("updateby <=", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLike(String value) {
            addCriterion("updateby like", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotLike(String value) {
            addCriterion("updateby not like", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIn(List<String> values) {
            addCriterion("updateby in", values, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotIn(List<String> values) {
            addCriterion("updateby not in", values, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyBetween(String value1, String value2) {
            addCriterion("updateby between", value1, value2, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotBetween(String value1, String value2) {
            addCriterion("updateby not between", value1, value2, "updateby");
            return (Criteria) this;
        }

        public Criteria andImgIsNull() {
            addCriterion("img is null");
            return (Criteria) this;
        }

        public Criteria andImgIsNotNull() {
            addCriterion("img is not null");
            return (Criteria) this;
        }

        public Criteria andImgEqualTo(String value) {
            addCriterion("img =", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotEqualTo(String value) {
            addCriterion("img <>", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgGreaterThan(String value) {
            addCriterion("img >", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgGreaterThanOrEqualTo(String value) {
            addCriterion("img >=", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLessThan(String value) {
            addCriterion("img <", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLessThanOrEqualTo(String value) {
            addCriterion("img <=", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLike(String value) {
            addCriterion("img like", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotLike(String value) {
            addCriterion("img not like", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgIn(List<String> values) {
            addCriterion("img in", values, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotIn(List<String> values) {
            addCriterion("img not in", values, "img");
            return (Criteria) this;
        }

        public Criteria andImgBetween(String value1, String value2) {
            addCriterion("img between", value1, value2, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotBetween(String value1, String value2) {
            addCriterion("img not between", value1, value2, "img");
            return (Criteria) this;
        }

        public Criteria andWifiIsNull() {
            addCriterion("wifi is null");
            return (Criteria) this;
        }

        public Criteria andWifiIsNotNull() {
            addCriterion("wifi is not null");
            return (Criteria) this;
        }

        public Criteria andWifiEqualTo(Boolean value) {
            addCriterion("wifi =", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiNotEqualTo(Boolean value) {
            addCriterion("wifi <>", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiGreaterThan(Boolean value) {
            addCriterion("wifi >", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiGreaterThanOrEqualTo(Boolean value) {
            addCriterion("wifi >=", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiLessThan(Boolean value) {
            addCriterion("wifi <", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiLessThanOrEqualTo(Boolean value) {
            addCriterion("wifi <=", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiIn(List<Boolean> values) {
            addCriterion("wifi in", values, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiNotIn(List<Boolean> values) {
            addCriterion("wifi not in", values, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiBetween(Boolean value1, Boolean value2) {
            addCriterion("wifi between", value1, value2, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiNotBetween(Boolean value1, Boolean value2) {
            addCriterion("wifi not between", value1, value2, "wifi");
            return (Criteria) this;
        }

        public Criteria andAttr1IsNull() {
            addCriterion("attr1 is null");
            return (Criteria) this;
        }

        public Criteria andAttr1IsNotNull() {
            addCriterion("attr1 is not null");
            return (Criteria) this;
        }

        public Criteria andAttr1EqualTo(String value) {
            addCriterion("attr1 =", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1NotEqualTo(String value) {
            addCriterion("attr1 <>", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1GreaterThan(String value) {
            addCriterion("attr1 >", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1GreaterThanOrEqualTo(String value) {
            addCriterion("attr1 >=", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1LessThan(String value) {
            addCriterion("attr1 <", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1LessThanOrEqualTo(String value) {
            addCriterion("attr1 <=", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1Like(String value) {
            addCriterion("attr1 like", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1NotLike(String value) {
            addCriterion("attr1 not like", value, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1In(List<String> values) {
            addCriterion("attr1 in", values, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1NotIn(List<String> values) {
            addCriterion("attr1 not in", values, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1Between(String value1, String value2) {
            addCriterion("attr1 between", value1, value2, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr1NotBetween(String value1, String value2) {
            addCriterion("attr1 not between", value1, value2, "attr1");
            return (Criteria) this;
        }

        public Criteria andAttr2IsNull() {
            addCriterion("attr2 is null");
            return (Criteria) this;
        }

        public Criteria andAttr2IsNotNull() {
            addCriterion("attr2 is not null");
            return (Criteria) this;
        }

        public Criteria andAttr2EqualTo(Integer value) {
            addCriterion("attr2 =", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2NotEqualTo(Integer value) {
            addCriterion("attr2 <>", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2GreaterThan(Integer value) {
            addCriterion("attr2 >", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2GreaterThanOrEqualTo(Integer value) {
            addCriterion("attr2 >=", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2LessThan(Integer value) {
            addCriterion("attr2 <", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2LessThanOrEqualTo(Integer value) {
            addCriterion("attr2 <=", value, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2In(List<Integer> values) {
            addCriterion("attr2 in", values, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2NotIn(List<Integer> values) {
            addCriterion("attr2 not in", values, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2Between(Integer value1, Integer value2) {
            addCriterion("attr2 between", value1, value2, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr2NotBetween(Integer value1, Integer value2) {
            addCriterion("attr2 not between", value1, value2, "attr2");
            return (Criteria) this;
        }

        public Criteria andAttr3IsNull() {
            addCriterion("attr3 is null");
            return (Criteria) this;
        }

        public Criteria andAttr3IsNotNull() {
            addCriterion("attr3 is not null");
            return (Criteria) this;
        }

        public Criteria andAttr3EqualTo(Boolean value) {
            addCriterion("attr3 =", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3NotEqualTo(Boolean value) {
            addCriterion("attr3 <>", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3GreaterThan(Boolean value) {
            addCriterion("attr3 >", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3GreaterThanOrEqualTo(Boolean value) {
            addCriterion("attr3 >=", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3LessThan(Boolean value) {
            addCriterion("attr3 <", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3LessThanOrEqualTo(Boolean value) {
            addCriterion("attr3 <=", value, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3In(List<Boolean> values) {
            addCriterion("attr3 in", values, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3NotIn(List<Boolean> values) {
            addCriterion("attr3 not in", values, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3Between(Boolean value1, Boolean value2) {
            addCriterion("attr3 between", value1, value2, "attr3");
            return (Criteria) this;
        }

        public Criteria andAttr3NotBetween(Boolean value1, Boolean value2) {
            addCriterion("attr3 not between", value1, value2, "attr3");
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