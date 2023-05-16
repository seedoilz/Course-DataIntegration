SELECT uid,
       tran_flag,
       tran_amt,
       cac_intc_pr,
       dr_cr_code,
       pay_term
FROM dm.dm_v_tr_huanx_mx INTO OUTFILE '/home/vboxuser/dm_v_tr_huanx_mx.csv' FORMAT CSV

SELECT uid,
       acct_char,
       curr_type,
       prod_code,
       is_secu_card,
       acct_sts,
       frz_sts,
       stp_sts,
       bal,
       avg_mth,
       avg_qur,
       avg_year
FROM dm.pri_cust_asset_acct_info INTO OUTFILE '/home/vboxuser/pri_cust_asset_acct_info.csv' FORMAT CSV

SELECT uid,
       all_bal,
       avg_mth,
       avg_qur,
       avg_year,
       sa_bal,
       td_bal,
       fin_bal,
       sa_crd_bal,
       td_crd_bal,
       sa_td_bal,
       ntc_bal,
       td_3m_bal,
       td_6m_bal,
       td_1y_bal,
       td_2y_bal,
       td_3y_bal,
       td_5y_bal,
       oth_td_bal,
       cd_bal
FROM dm.pri_cust_asset_info INTO OUTFILE '/home/vboxuser/pri_cust_asset_info.csv' FORMAT CSV

SELECT uid,
       marrige,
       education,
       prof_titl,
       is_employee,
       is_shareholder,
       is_black,
       is_contact,
       is_mgr_dep
FROM dm.pri_cust_base_info INTO OUTFILE '/home/vboxuser/pri_cust_base_info.csv' FORMAT CSV

SELECT uid,
       vouch_type,
       is_mortgage,
       is_online,
       is_extend,
       repay_type,
       five_class,
       overdue_class,
       overdue_flag,
       owed_int_flag,
       loan_use,
       defect_type,
       owed_int_in,
       owed_int_out,
       delay_bal,
       acct_sts,
       book_acct_amt,
       pro_char_ori,
       pay_type,
       guar_amount,
       guar_eva_value
FROM dm.pri_cust_liab_acct_info INTO OUTFILE '/home/vboxuser/pri_cust_liab_acct_info.csv' FORMAT CSV

SELECT uid, dr_amt
FROM dm.dm_v_tr_sa_mx INTO OUTFILE '/home/xboxuser/dm_v_tr_sa_mx.csv'
FORMAT CSV

SELECT uid,
       buss_type,
       curr_type,
       buss_amt,
       bal,
       norm_bal,
       dlay_amt,
       dull_amt,
       bad_debt_amt,
       owed_int_in,
       owed_int_out,
       fine_pr_int,
       fine_intr_int,
       dlay_days,
       due_intr_days,
       loan_use,
       pay_type,
       pay_freq,
       vouch_type,
       ten_class
FROM dm.dm_v_tr_duebill_mx INTO OUTFILE '/home/vboxuser/dm_v_tr_duebill_mx.csv'
FORMAT CSV

SELECT uid,
       tran_amt,
       tran_sts,
       busi_type
FROM dm.dm_v_tr_dsf_mx INTO OUTFILE '/home/vboxuser/dm_v_tr_dsf_mx.csv'
FORMAT CSV

SELECT uid,
       tran_type,
       tran_amt,
       tran_amt_sign
FROM dm.dm_v_tr_djk_mx INTO OUTFILE '/home/vboxuser/dm_v_tr_djk_mx.csv'
FORMAT CSV

SELECT uid,
       occur_type,
       is_credit_cyc,
       curr_type,
       buss_amt,
       loan_pert,
       term_year,
       term_mth,
       term_day,
       pay_type,
       pay_times,
       direction,
       loan_use,
       pay_source,
       vouch_type,
       apply_type,
       owed_int_in,
       owed_int_out,
       dlay_days,
       five_class,
       fine_pr_int,
       fine_intr_int,
       is_bad,
       frz_amt,
       due_intr_days,
       is_vc_vouch,
       loan_use_add,
       finsh_type
FROM dm.dm_v_tr_contract_mx INTO OUTFILE '/home/vboxuser/dm_v_tr_contract_mx.csv'
FORMAT CSV

SELECT uid,
       recom_no,
       mp_number,
       mp_number,
       mp_type,
       mp_status,
       total_amt,
       total_mths,
       instl_cnt,
       rem_fee,
       rec_fee
FROM dm.dm_v_as_djkfq_info INTO OUTFILE '/home/vboxuser/dm_v_as_djkfq_info.csv'
FORMAT CSV

SELECT uid,
       card_sts,
       is_withdrw,
       is_transfer,
       is_deposit,
       is_purchse,
       cred_limit,
       deposit,
       over_draft,
       dlay_amt,
       five_class,
       is_etc,
       bal
FROM dm.dm_v_as_djk_info INTO OUTFILE '/home/vboxuser/dm_v_as_djk_info.csv'
FORMAT CSV

SELECT uid,
       tran_amt,
       bal,
       dr_cr_code,
       pprd_amotz_intr,
       tran_type,
       pay_term
FROM dm.dm_v_tr_huanb_mx INTO OUTFILE '/home/vboxuser/dm_v_tr_huanb_mx.csv'
FORMAT CSV

SELECT uid, buss_type, curr_type,buss_amt,bal,norm_bal,dlay_amt,dull_amt,bad_debt_amt,owed_int_in,owed_int_out,fine_pr_int,fine_intr_int,dlay_days,due_intr_days,loan_use,pay_type,pay_freq,vouch_type,ten_class
FROM dm.dm_v_tr_duebill_mx
INTO OUTFILE '/home/vboxuser/dm_v_tr_duebill_mx.csv' FORMAT CSV

SELECT uid, tran_amt_fen
FROM dm.dm_v_tr_etc_mx
INTO OUTFILE '/home/vboxuser/dm_v_tr_etc_mx.csv' FORMAT CSV

SELECT uid, tran_code,sys_type,tran_amt
FROM dm.dm_v_tr_grwy_mx
INTO OUTFILE '/home/vboxuser/dm_v_tr_grwy_mx.csv' FORMAT CSV

SELECT uid, tran_amt, is_secu_card
FROM dm.dm_v_tr_gzdf_mx
INTO OUTFILE '/home/vboxuser/dm_v_tr_gzdf_mx.csv' FORMAT CSV

SELECT uid, tran_sts
FROM dm.dm_v_tr_sbyb_mx
INTO OUTFILE '/home/vboxuser/dm_v_tr_sbyb_mx.csv' FORMAT CSV

SELECT uid, tran_sts
FROM dm.dm_v_tr_sdrq_mx
INTO OUTFILE '/home/vboxuser/dm_v_tr_sdrq_mx.csv' FORMAT CSV

SELECT uid, tran_sts, current_status
FROM dm.dm_v_tr_shop_mx
INTO OUTFILE '/home/vboxuser/dm_v_tr_shop_mx.csv' FORMAT CSV

SELECT uid, tran_sts, tran_amt
FROM dm.dm_v_tr_sjyh_mx
INTO OUTFILE '/home/vboxuser/dm_v_tr_sjyh_mx.csv' FORMAT CSV

SELECT uid,cust_no,all_bal,bad_bal,due_intr,delay_bal
FROM dm.pri_cust_liab_info
INTO OUTFILE '/home/vboxuser/pri_cust_liab_info.csv' FORMAT CSV

