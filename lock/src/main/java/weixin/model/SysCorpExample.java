package weixin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysCorpExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysCorpExample() {
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

        public Criteria andCorpNameIsNull() {
            addCriterion("corp_name is null");
            return (Criteria) this;
        }

        public Criteria andCorpNameIsNotNull() {
            addCriterion("corp_name is not null");
            return (Criteria) this;
        }

        public Criteria andCorpNameEqualTo(String value) {
            addCriterion("corp_name =", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameNotEqualTo(String value) {
            addCriterion("corp_name <>", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameGreaterThan(String value) {
            addCriterion("corp_name >", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameGreaterThanOrEqualTo(String value) {
            addCriterion("corp_name >=", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameLessThan(String value) {
            addCriterion("corp_name <", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameLessThanOrEqualTo(String value) {
            addCriterion("corp_name <=", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameLike(String value) {
            addCriterion("corp_name like", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameNotLike(String value) {
            addCriterion("corp_name not like", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameIn(List<String> values) {
            addCriterion("corp_name in", values, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameNotIn(List<String> values) {
            addCriterion("corp_name not in", values, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameBetween(String value1, String value2) {
            addCriterion("corp_name between", value1, value2, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameNotBetween(String value1, String value2) {
            addCriterion("corp_name not between", value1, value2, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlIsNull() {
            addCriterion("corp_square_logo_url is null");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlIsNotNull() {
            addCriterion("corp_square_logo_url is not null");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlEqualTo(String value) {
            addCriterion("corp_square_logo_url =", value, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlNotEqualTo(String value) {
            addCriterion("corp_square_logo_url <>", value, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlGreaterThan(String value) {
            addCriterion("corp_square_logo_url >", value, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlGreaterThanOrEqualTo(String value) {
            addCriterion("corp_square_logo_url >=", value, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlLessThan(String value) {
            addCriterion("corp_square_logo_url <", value, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlLessThanOrEqualTo(String value) {
            addCriterion("corp_square_logo_url <=", value, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlLike(String value) {
            addCriterion("corp_square_logo_url like", value, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlNotLike(String value) {
            addCriterion("corp_square_logo_url not like", value, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlIn(List<String> values) {
            addCriterion("corp_square_logo_url in", values, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlNotIn(List<String> values) {
            addCriterion("corp_square_logo_url not in", values, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlBetween(String value1, String value2) {
            addCriterion("corp_square_logo_url between", value1, value2, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpSquareLogoUrlNotBetween(String value1, String value2) {
            addCriterion("corp_square_logo_url not between", value1, value2, "corpSquareLogoUrl");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameIsNull() {
            addCriterion("corp_full_name is null");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameIsNotNull() {
            addCriterion("corp_full_name is not null");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameEqualTo(String value) {
            addCriterion("corp_full_name =", value, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameNotEqualTo(String value) {
            addCriterion("corp_full_name <>", value, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameGreaterThan(String value) {
            addCriterion("corp_full_name >", value, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameGreaterThanOrEqualTo(String value) {
            addCriterion("corp_full_name >=", value, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameLessThan(String value) {
            addCriterion("corp_full_name <", value, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameLessThanOrEqualTo(String value) {
            addCriterion("corp_full_name <=", value, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameLike(String value) {
            addCriterion("corp_full_name like", value, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameNotLike(String value) {
            addCriterion("corp_full_name not like", value, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameIn(List<String> values) {
            addCriterion("corp_full_name in", values, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameNotIn(List<String> values) {
            addCriterion("corp_full_name not in", values, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameBetween(String value1, String value2) {
            addCriterion("corp_full_name between", value1, value2, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpFullNameNotBetween(String value1, String value2) {
            addCriterion("corp_full_name not between", value1, value2, "corpFullName");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeIsNull() {
            addCriterion("corp_wxqrcode is null");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeIsNotNull() {
            addCriterion("corp_wxqrcode is not null");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeEqualTo(String value) {
            addCriterion("corp_wxqrcode =", value, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeNotEqualTo(String value) {
            addCriterion("corp_wxqrcode <>", value, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeGreaterThan(String value) {
            addCriterion("corp_wxqrcode >", value, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeGreaterThanOrEqualTo(String value) {
            addCriterion("corp_wxqrcode >=", value, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeLessThan(String value) {
            addCriterion("corp_wxqrcode <", value, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeLessThanOrEqualTo(String value) {
            addCriterion("corp_wxqrcode <=", value, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeLike(String value) {
            addCriterion("corp_wxqrcode like", value, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeNotLike(String value) {
            addCriterion("corp_wxqrcode not like", value, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeIn(List<String> values) {
            addCriterion("corp_wxqrcode in", values, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeNotIn(List<String> values) {
            addCriterion("corp_wxqrcode not in", values, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeBetween(String value1, String value2) {
            addCriterion("corp_wxqrcode between", value1, value2, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpWxqrcodeNotBetween(String value1, String value2) {
            addCriterion("corp_wxqrcode not between", value1, value2, "corpWxqrcode");
            return (Criteria) this;
        }

        public Criteria andCorpScaleIsNull() {
            addCriterion("corp_scale is null");
            return (Criteria) this;
        }

        public Criteria andCorpScaleIsNotNull() {
            addCriterion("corp_scale is not null");
            return (Criteria) this;
        }

        public Criteria andCorpScaleEqualTo(String value) {
            addCriterion("corp_scale =", value, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleNotEqualTo(String value) {
            addCriterion("corp_scale <>", value, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleGreaterThan(String value) {
            addCriterion("corp_scale >", value, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleGreaterThanOrEqualTo(String value) {
            addCriterion("corp_scale >=", value, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleLessThan(String value) {
            addCriterion("corp_scale <", value, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleLessThanOrEqualTo(String value) {
            addCriterion("corp_scale <=", value, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleLike(String value) {
            addCriterion("corp_scale like", value, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleNotLike(String value) {
            addCriterion("corp_scale not like", value, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleIn(List<String> values) {
            addCriterion("corp_scale in", values, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleNotIn(List<String> values) {
            addCriterion("corp_scale not in", values, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleBetween(String value1, String value2) {
            addCriterion("corp_scale between", value1, value2, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpScaleNotBetween(String value1, String value2) {
            addCriterion("corp_scale not between", value1, value2, "corpScale");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryIsNull() {
            addCriterion("corp_industry is null");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryIsNotNull() {
            addCriterion("corp_industry is not null");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryEqualTo(String value) {
            addCriterion("corp_industry =", value, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryNotEqualTo(String value) {
            addCriterion("corp_industry <>", value, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryGreaterThan(String value) {
            addCriterion("corp_industry >", value, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryGreaterThanOrEqualTo(String value) {
            addCriterion("corp_industry >=", value, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryLessThan(String value) {
            addCriterion("corp_industry <", value, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryLessThanOrEqualTo(String value) {
            addCriterion("corp_industry <=", value, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryLike(String value) {
            addCriterion("corp_industry like", value, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryNotLike(String value) {
            addCriterion("corp_industry not like", value, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryIn(List<String> values) {
            addCriterion("corp_industry in", values, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryNotIn(List<String> values) {
            addCriterion("corp_industry not in", values, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryBetween(String value1, String value2) {
            addCriterion("corp_industry between", value1, value2, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andCorpIndustryNotBetween(String value1, String value2) {
            addCriterion("corp_industry not between", value1, value2, "corpIndustry");
            return (Criteria) this;
        }

        public Criteria andLocationIsNull() {
            addCriterion("location is null");
            return (Criteria) this;
        }

        public Criteria andLocationIsNotNull() {
            addCriterion("location is not null");
            return (Criteria) this;
        }

        public Criteria andLocationEqualTo(String value) {
            addCriterion("location =", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotEqualTo(String value) {
            addCriterion("location <>", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThan(String value) {
            addCriterion("location >", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThanOrEqualTo(String value) {
            addCriterion("location >=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThan(String value) {
            addCriterion("location <", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThanOrEqualTo(String value) {
            addCriterion("location <=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLike(String value) {
            addCriterion("location like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotLike(String value) {
            addCriterion("location not like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationIn(List<String> values) {
            addCriterion("location in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotIn(List<String> values) {
            addCriterion("location not in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationBetween(String value1, String value2) {
            addCriterion("location between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotBetween(String value1, String value2) {
            addCriterion("location not between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andAgentidIsNull() {
            addCriterion("agentid is null");
            return (Criteria) this;
        }

        public Criteria andAgentidIsNotNull() {
            addCriterion("agentid is not null");
            return (Criteria) this;
        }

        public Criteria andAgentidEqualTo(Integer value) {
            addCriterion("agentid =", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotEqualTo(Integer value) {
            addCriterion("agentid <>", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidGreaterThan(Integer value) {
            addCriterion("agentid >", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidGreaterThanOrEqualTo(Integer value) {
            addCriterion("agentid >=", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidLessThan(Integer value) {
            addCriterion("agentid <", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidLessThanOrEqualTo(Integer value) {
            addCriterion("agentid <=", value, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidIn(List<Integer> values) {
            addCriterion("agentid in", values, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotIn(List<Integer> values) {
            addCriterion("agentid not in", values, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidBetween(Integer value1, Integer value2) {
            addCriterion("agentid between", value1, value2, "agentid");
            return (Criteria) this;
        }

        public Criteria andAgentidNotBetween(Integer value1, Integer value2) {
            addCriterion("agentid not between", value1, value2, "agentid");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
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