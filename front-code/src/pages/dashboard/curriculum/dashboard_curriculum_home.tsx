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
import {JSX, useEffect, useState} from "react";
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
import {CurriculumClassAddModal} from "../../../components/modal/curriculum_class_add_modal.tsx";
import {CurriculumDelModal} from "../../../components/modal/curriculum_del_modal.tsx";
import {ClassEntity} from "../../../models/entity/class_entity.ts";
import {CurriculumClassEditModal} from "../../../components/modal/curriculum_class_edit_modal.tsx";

export function DashboardCurriculumHome({onHeaderHandler}: { onHeaderHandler: (header: string) => void }) {
    const navigate = useNavigate();

    const webInfo = useSelector((state: { webInfo: WebInfoEntity }) => state.webInfo);

    const [curriculumList, setCurriculumList] = useState<ListCurriculumEntity[]>([] as ListCurriculumEntity[]);
    const [curriculumTimeList, setCurriculumTimeList] = useState<ListCurriculumTimeEntity[]>([] as ListCurriculumTimeEntity[]);
    const [curriculumSelectTime, setCurriculumSelectTime] = useState<ListCurriculumTimeEntity>({} as ListCurriculumTimeEntity);
    const [classGrade, setClassGrade] = useState<ClassGradeEntity>({} as ClassGradeEntity);
    const [clazz, setClazz] = useState<ClassEntity>({} as ClassEntity);
    const [curriculum, setCurriculum] = useState<string>("");

    const [curriculumAddModal, setCurriculumAddModal] = useState<boolean>(false);
    const [curriculumClassAddModal, setCurriculumClassAddModal] = useState<boolean>(false);
    const [curriculumClassEditModal, setCurriculumClassEditModal] = useState<boolean>(false);
    const [curriculumDeleteModal, setCurriculumDeleteModal] = useState<boolean>(false);

    const [refresh, setRefresh] = useState<boolean>(false);
    const [mergeRefresh, setMergeRefresh] = useState<boolean>(false);

    const [debounceTimeout, setDebounceTimeout] = useState<number>();

    document.title = `${webInfo.name} - 课程表`;
    onHeaderHandler("课程表");

    useEffect(() => {
        const func = async () => {
            const [listApi, timeListApi] = await Promise.all([
                SelectCurriculumListAPI(""),
                SelectCurriculumTimeListAPI("")
            ]);

            if (!refresh || !mergeRefresh) {
                if (listApi?.output === "Success") setCurriculumList(listApi.data!);
                else message.warning(listApi?.error_message);
                if (!mergeRefresh && curriculum !== "" &&  curriculum !== "system-add") {
                    const func = async () => {
                        const getResp = await GetClassGradeAPI(curriculum);
                        if (getResp?.output === "Success") {
                            setClassGrade(getResp.data!);
                        } else {
                            message.warning(getResp?.error_message);
                        }
                    }
                    func().then();
                }
            }

            if (!refresh) {
                if (timeListApi?.output === "Success") setCurriculumTimeList(timeListApi.data!);
                else message.warning(listApi?.error_message);
            }
        }
        func().then();
        setRefresh(false);
        setMergeRefresh(false);
    }, [refresh, mergeRefresh, curriculum]);

    useEffect(() => {
        const func = async () => {
            const getResp = await GetClassGradeAPI(curriculum);
            if (getResp?.output === "Success") {
                setClassGrade(getResp.data!);
            } else {
                message.warning(getResp?.error_message);
            }
        }

        if (curriculum === "system-add") {
            setCurriculum("");
            setCurriculumAddModal(true);
        } else {
            if (curriculum !== "") {
                func().then();
            }
        }
        setRefresh(false);
    }, [curriculum]);

    useEffect(() => {
        if (classGrade.class_time_uuid === "system-add") {
            navigate("/dashboard/curriculum/time");
        } else {
            if (classGrade.class_time_uuid && classGrade.class_time_uuid !== "") {
                setCurriculumSelectTime(curriculumTimeList.find(item => item.class_time_market_uuid === classGrade.class_time_uuid)!);
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
    }, [classGrade.class_time_uuid, navigate]);

    function editCurriculumEdit(clazz: ClassEntity) {
        setCurriculumClassEditModal(true);
        setClazz(clazz);
    }

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
                        <div className="col-span-9 2xl:col-span-10">
                            <div className="bg-white rounded-lg shadow-md p-3">
                                {classGrade.class_time_uuid ? (
                                    <table
                                        className="min-w-full min-h-full divide-y-2 divide-gray-200 bg-white text-sm">
                                        <thead>
                                        <tr className="text-center font-bold">
                                            <th className="px-4 py-2 w-2/12">节次</th>
                                            <th className="px-4 py-2 w-1/12">周一</th>
                                            <th className="px-4 py-2 w-1/12">周二</th>
                                            <th className="px-4 py-2 w-1/12">周三</th>
                                            <th className="px-4 py-2 w-1/12">周四</th>
                                            <th className="px-4 py-2 w-1/12">周五</th>
                                            <th className="px-4 py-2 w-1/12">周六</th>
                                            <th className="px-4 py-2 w-1/12">周日</th>
                                        </tr>
                                        </thead>
                                        <tbody className="divide-y divide-gray-200 h-fulll">
                                        {curriculumSelectTime.timetable?.map((time, rowIndex) => (
                                            <tr key={rowIndex} className="text-center">
                                                <td className={`h-[50px] font-bold bg-gray-200 p-1 ${rowIndex === curriculumSelectTime.timetable.length - 1 ? "rounded-b-lg" : ""}`}>
                                                    第 {rowIndex + 1} 节
                                                    <div className="hidden lg:block text-sm text-gray-400">
                                                        <span>{time.startTime} - {time.endTime}</span>
                                                    </div>
                                                </td>
                                                {Array.from({length: 7}).map((_, dayIndex) => {
                                                    const courses = classGrade.class_list.filter(
                                                        (item) =>
                                                            item.start_tick === rowIndex && // 节次匹配
                                                            item.day_tick - 1 === dayIndex // 星期几匹配
                                                    );

                                                    if (courses.length > 0) {
                                                        const element = [] as JSX.Element[];
                                                        const maxRow = Math.max(...courses.map((item) => item.end_tick - item.start_tick));
                                                        for (const coursesKey of courses) {
                                                            const rowSpan = coursesKey.end_tick - coursesKey.start_tick;
                                                            const colors = ["bg-blue-500", "bg-green-500", "bg-yellow-500", "bg-red-500", "bg-purple-500", "bg-pink-500"];
                                                            const hash = [...coursesKey.name].reduce((acc, char) => acc + char.charCodeAt(0), 0);
                                                            const colorClass = colors[hash % colors.length];
                                                            element.push(
                                                                <div key={coursesKey.class_uuid}
                                                                     style={{height: rowSpan * 50}}
                                                                     onClick={() => editCurriculumEdit(coursesKey)}
                                                                     className={`${colorClass} flex-1 text-white p-0.5 rounded-md shadow flex flex-col justify-between`}>
                                                                    <div className="text-xs text-gray-200">{
                                                                        coursesKey.teacher}
                                                                    </div>
                                                                    <div
                                                                        className="font-bold overflow-hidden overflow-ellipsis">
                                                                        {coursesKey.name}
                                                                    </div>
                                                                    <div className="text-xs text-gray-300">
                                                                        {coursesKey.location}
                                                                    </div>
                                                                </div>
                                                            );
                                                        }
                                                        return (
                                                            <td key={dayIndex} rowSpan={maxRow}
                                                                className={"p-0.5 align-top h-full"}>
                                                                <div className={"flex gap-x-0.5 h-full"}>
                                                                    {element}
                                                                </div>
                                                            </td>
                                                        );
                                                    } else if (classGrade.class_list.some((item) => item.start_tick < rowIndex && item.end_tick >= rowIndex && item.day_tick + 1 < dayIndex)) {
                                                        return null;
                                                    } else {
                                                        return <td key={dayIndex} className="p-1"></td>;
                                                    }
                                                })}
                                            </tr>
                                        ))}
                                        </tbody>
                                    </table>
                                ) : (
                                    <div className="text-2xl font-bold text-center text-gray-500">
                                        该课程表未设置课表时间，请先设置课表时间
                                    </div>
                                )}
                            </div>
                        </div>
                        <div className={"col-span-3 2xl:col-span-2"}>
                            <div className={"grid grid-cols-1 gap-3 bg-white shadow-md p-3 rounded-lg"}>
                                <div className={"flex gap-3 text-white"}>
                                    <button onClick={() => setCurriculumClassAddModal(true)}
                                            className={"flex-1 py-1.5 px-2 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"}>
                                        添加课程
                                    </button>
                                    <button onClick={() => setCurriculumDeleteModal(true)}
                                            className={"flex-1 py-1.5 px-2 rounded-lg shadow bg-red-500 hover:bg-red-600 transition"}>
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
            <CurriculumAddModal propOpen={curriculumAddModal} emit={setCurriculumAddModal} timeList={curriculumTimeList}
                                refresh={setRefresh}/>
            <CurriculumClassAddModal propOpen={curriculumClassAddModal} refresh={setRefresh} curriculum={curriculum}
                                     emit={setCurriculumClassAddModal} curriculumSelectTime={curriculumSelectTime}
                                     emitCurriculum={setCurriculum} mergeRefresh={setMergeRefresh}/>
            <CurriculumDelModal propOpen={curriculumDeleteModal} emit={setCurriculumDeleteModal} refresh={setRefresh}
                                classGrade={classGrade} reset={setCurriculum}/>
            <CurriculumClassEditModal propOpen={curriculumClassEditModal} clazz={clazz} curriculum={curriculum}
                                      emit={setCurriculumClassEditModal} curriculumSelectTime={curriculumSelectTime}
                                      mergeRefresh={setMergeRefresh}/>
        </>
    );
}
