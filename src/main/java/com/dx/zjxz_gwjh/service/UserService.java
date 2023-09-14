package com.dx.zjxz_gwjh.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dx.zjxz_gwjh.entity.QUserEntity;
import com.dx.zjxz_gwjh.entity.UserEntity;
import com.dx.zjxz_gwjh.filter.UserFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.repository.UserRepository;
import com.dx.easyspringweb.biz.jpa.service.JpaBaseUserService;
import com.dx.easyspringweb.biz.model.User;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.rpc.account.enums.AccountStatus;
import com.querydsl.core.BooleanBuilder;

@Service
public class UserService extends JpaBaseUserService<RDUserSession, UserEntity, UserFilter, String> {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public PagingData<UserEntity> queryList(QueryRequest<UserFilter> query)
            throws ServiceException {
        QUserEntity q = QUserEntity.userEntity;

        BooleanBuilder predicate = new BooleanBuilder();
        UserFilter filter = query.getFilter();
        if (filter != null) {
            if (StringUtils.hasText(filter.getUserName())) {
                predicate.and(q.userName.contains(filter.getUserName()));
            }

            if (StringUtils.hasText(filter.getRealName())) {
                predicate.and(q.realName.contains(filter.getRealName()));
            }

            if (StringUtils.hasText(filter.getPhoneNumber())) {
                predicate.and(q.phoneNumber.contains(filter.getPhoneNumber()));
            }

            if (StringUtils.hasText(filter.getDeptId())) {
                predicate.and(q.depts.any().id.eq(filter.getDeptId()));
            }
            if (StringUtils.hasText(filter.getRoleId())) {
                predicate.and(q.roles.any().id.eq(filter.getRoleId()));
            }

            if (filter.getStatus() != null) {
                predicate.and(q.status.eq(filter.getStatus()));
            }
        }
        return this.queryList(predicate, query.getPageInfo());
    }

    @Override
    public UserEntity create(UserEntity entity) throws ServiceException {
        if (entity.getStatus() == null) {
            entity.setStatus(AccountStatus.NORMAL);
        }
        return super.createEntity(entity);
    }

    @Override
    public RDUserSession createSession(User<String> user) throws ServiceException {
        UserEntity entity = (UserEntity) user;
        RDUserSession session = super.createSession(user);
        return session;
    }

    public Iterable<UserEntity> getByRole(String roleId) {
        BooleanBuilder predicate = new BooleanBuilder();
        QUserEntity q = QUserEntity.userEntity;
        predicate.and(q.roles.any().id.eq(roleId));

        return this.repository.findAll(predicate);
    }
}
