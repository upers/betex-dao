package com.vareger.service.tools;

import com.vareger.models.UserBid;
import com.vareger.servicies.UserBidService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

@Component
@Slf4j
public class SafeUserBidService {

    @Autowired
    private UserBidService userBidService;

    /**
     * This method will be execute while it will get access to serialize transaction mode.
     * userBid
     * salt
     * @return
     */
    public UserBid rewardUser(Long salt, UserBid userBid) {
        try {
            return userBidService.rewardUser(salt, userBid);
        } catch (CannotCreateTransactionException | CannotAcquireLockException | TransactionSystemException | TransactionException e) {
            log.error(e.getMessage());
            log.error("Could not serialize access to user bid now. Try do transaction in 0.2 seconds.");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e1) {
                log.error(e1.getMessage(), e1);
            }

            return rewardUser(salt, userBid);
        }
    }
}
