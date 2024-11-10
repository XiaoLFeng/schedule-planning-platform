/*
 * ***************************************************************************************
 * author: XiaoLFeng(https://www.x-lf.com)
 * about:
 *   The project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * licenseStatement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *     in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *     caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *     you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

import {useSelector} from "react-redux";
import {WebInfoEntity} from "../../../models/entity/web_info_entity.ts";
import {useEffect, useState} from "react";
import {SelectCurriculumListAPI, SelectCurriculumTimeListAPI} from "../../../interface/select_list_api.ts";
import {ListCurriculumEntity} from "../../../models/entity/list_curriculum_entity.ts";
import {message} from "antd";
import {CurriculumAddModal} from "../../../components/modal/curriculum_add_modal.tsx";
import {EditClassGradeAPI, GetClassGradeAPI} from "../../../interface/curriculum_api.ts";
import {ClassGradeEntity} from "../../../models/entity/class_grade_entity.ts";
import dayjs from "dayjs";
import {ListCurriculumTimeEntity} from "../../../models/entity/list_curriculum_time_entity.ts";
import {useNavigate} from "react-router-dom";
import {ClassGradeDTO} from "../../../models/dto/class_grade_create_dto.ts";

export function DashboardCurriculumHome({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {
    const navigate = useNavigate();

    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    const [curriculumList, setCurriculumList] = useState<ListCurriculumEntity[]>([] as ListCurriculumEntity[]);
    const [curriculumTimeList, setCurriculumTimeList] = useState<ListCurriculumTimeEntity[]>([] as ListCurriculumTimeEntity[]);
    const [classGrade, setClassGrade] = useState<ClassGradeEntity>({} as ClassGradeEntity);
    const [curriculum, setCurriculum] = useState<string>("");

    const [curriculumAddModal, setCurriculumAddModal] = useState<boolean>(false);

    const [refresh, setRefresh] = useState<boolean>(false);

    const [debounceTimeout, setDebounceTimeout] = useState<number>();

    document.title = `${webInfo.name} - 课程表`;
    onHeaderHandler("课程表");

    useEffect(() => {
        if (!refresh) {
            const func = async () => {
                const [listApi, timeListApi] = await Promise.all([
                    SelectCurriculumListAPI(""),
                    SelectCurriculumTimeListAPI("")
                ]);

                if (listApi?.output === "Success") setCurriculumList(listApi.data!);
                else message.warning(listApi?.error_message);

                if (timeListApi?.output === "Success") setCurriculumTimeList(timeListApi.data!);
                else message.warning(listApi?.error_message);
            }
            func().then();
        }
        setRefresh(false);
    }, [refresh]);

    useEffect(() => {
        if (curriculum === "system-add") {
            setCurriculum("");
            setCurriculumAddModal(true);
        } else {
            const func = async () => {
                const getResp = await GetClassGradeAPI(curriculum);
                if (getResp?.output === "Success") {
                    setClassGrade(getResp.data!);
                } else {
                    message.warning(getResp?.error_message);
                }
            }
            if (curriculum !== "") {
                func().then();
            }
        }
    }, [curriculum]);

    useEffect(() => {
        if (classGrade.class_time_uuid === "system-add") {
            navigate("/dashboard/curriculum/time");
        } else {
            if (classGrade.class_time_uuid && classGrade.class_time_uuid !== "") {
                console.debug("classGrade", !classGrade.class_time_uuid);
                clearTimeout(debounceTimeout);
                setDebounceTimeout(setTimeout(async () => {
                    const makeBody = {
                        grade_name: classGrade.nickname,
                        semester_begin: classGrade.semester_begin,
                        semester_end: classGrade.semester_end,
                        class_time_uuid: classGrade.class_time_uuid
                    } as ClassGradeDTO;
                    const getResp = await EditClassGradeAPI(classGrade.class_grade_uuid, makeBody);
                    if (getResp?.output === "Success") {
                        setRefresh(true);
                    } else {
                        message.warning(getResp?.error_message);
                    }
                }, 300)); // 设置300ms延迟
            }
        }
    }, [classGrade, navigate]);
    return (
        <>
            <div className={"grid lg:grid-cols-12 gap-3"}>
                <div className={"col-span-full flex justify-between gap-3"}>
                    <div className={"flex-1"}>
                        <label htmlFor="curriculum"
                               className="block text-sm font-medium text-gray-900">课表</label>
                        <select name="curriculum"
                                id="curriculum"
                                value={curriculum}
                                onChange={(e) => setCurriculum(e.target.value)}
                                className="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm shadow-sm transition">
                            <option value="" selected={true}>请选课课表</option>
                            {curriculumList.map((item, index) => (
                                <option key={index} value={item.class_grade_uuid}>{item.nickname}</option>
                            ))}
                            <option value="system-add">添加+</option>
                        </select>
                    </div>
                    <div className={"flex flex-col gap-3 text-white items-end justify-end"}>
                        <button onClick={() => navigate("/dashboard/curriculum/time")}
                                className={"py-1.5 px-4 rounded-lg shadow bg-emerald-500 hover:bg-emerald-600 transition"}>
                            课程时间管理
                        </button>
                    </div>
                </div>
                {curriculum ? (
                    <>
                        <div className={"col-span-8"}>
                            <div className={"grid grid-cols-2 gap-3"}>
                                <div className={"col-span-2 bg-white rounded-lg shadow-md p-3"}>
                                    {classGrade.class_time_uuid ? (
                                        <div/>
                                    ) : (
                                        <div className={"text-2xl font-bold text-center text-gray-500"}>
                                            该课程表未设置课表时间，请先设置课表时间
                                        </div>
                                    )}
                                </div>
                            </div>
                        </div>
                        <div className={"col-span-4"}>
                            <div className={"grid grid-cols-1 gap-3 bg-white shadow-md p-3 rounded-lg"}>
                                <div className={"flex gap-3 text-white"}>
                                    <button
                                        className={"flex-1 py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"}>
                                        添加课程
                                    </button>
                                    <button
                                        className={"flex-1 py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition"}>
                                        删除课程表
                                    </button>
                                </div>
                                <div className={"px-10"}>
                                    <hr className={"border-2 rounded-full"}/>
                                </div>
                                <div className={"grid grid-cols-1 gap-3"}>
                                    <div className={""}>
                                        <label htmlFor="HeadlineAct"
                                               className="block text-sm font-medium text-gray-900">课表时间</label>
                                        <select
                                            name="HeadlineAct"
                                            id="HeadlineAct"
                                            value={classGrade.class_time_uuid ? classGrade.class_time_uuid : ""}
                                            onChange={(e) => {
                                                setClassGrade({
                                                    ...classGrade,
                                                    class_time_uuid: e.target.value
                                                } as ClassGradeEntity)
                                            }}
                                            className="mt-1.5 w-full rounded-lg border-gray-300 text-gray-700 sm:text-sm transition"
                                        >
                                            <option value="">请选择课表时间</option>
                                            {curriculumTimeList.map((item, index) => (
                                                <option key={index}
                                                        value={item.class_time_market_uuid}>{item.name}</option>
                                            ))}
                                            <option value="system-add">添加+</option>
                                        </select>
                                    </div>
                                </div>
                                <div className={"px-10"}>
                                    <hr className={"border-2 rounded-full"}/>
                                </div>
                                <div className={"grid grid-cols-1 gap-3"}>
                                    <div className={"bg-gray-100/50 p-3 rounded-lg shadow"}>
                                        <div className={"grid gap-1"}>
                                            <div className={"flex justify-between"}>
                                                <span>开始时间</span>
                                                <span>{classGrade?.semester_begin ? dayjs(classGrade.semester_begin).format("YYYY年 MM月 DD日") : "NULL"}</span>
                                            </div>
                                            <div className={"flex justify-between"}>
                                                <span>结束时间</span>
                                                <span>{classGrade?.semester_end ? dayjs(classGrade.semester_end).format("YYYY年 MM月 DD日") : "NULL"}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </>
                ) : (
                    <div/>
                )}
            </div>
            <CurriculumAddModal propOpen={curriculumAddModal} emit={(value) => setCurriculumAddModal(value)}
                                timeList={curriculumTimeList}
                                refresh={(value) => setRefresh(value)}/>
        </>
    );
}
