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

import {useEffect, useRef, useState} from "react";
import {message, Modal} from "antd";
import {ClassEntity} from "../../models/entity/class_entity.ts";
import {DashOutlined} from "@ant-design/icons";
import {ListCurriculumTimeEntity} from "../../models/entity/list_curriculum_time_entity.ts";
import {MoveMutiClassAPI} from "../../interface/curriculum_api.ts";
import {ClassMutiMoveDTO} from "../../models/dto/class_muti_move_dto.ts";

export function CurriculumClassEditModal({propOpen, clazz, curriculum, curriculumSelectTime, emit, mergeRefresh}: {
    propOpen: boolean;
    clazz: ClassEntity;
    curriculum: string;
    curriculumSelectTime: ListCurriculumTimeEntity;
    mergeRefresh: (data: boolean) => void;
    emit: (open: boolean) => void;
}) {
    const [open, setOpen] = useState<boolean>(false);
    const [copyClazz, setCopyClazz] = useState<ClassEntity>({} as ClassEntity);
    const [clazzEdit, setClazzEdit] = useState<ClassEntity>({} as ClassEntity);
    const [selectedWeeks, setSelectedWeeks] = useState<boolean[]>([]);

    const localTeacher = useRef<string[]>(JSON.parse(localStorage.getItem("local_class_teacher") || "[]") as string[]);

    useEffect(() => {
        setSelectedWeeks(Array(20).fill(false));
        setOpen(propOpen);
        setClazzEdit(clazz);
        setCopyClazz(clazz);
        if (clazzEdit.week) {
            clazzEdit.week.forEach((week) => {
                const index = week - 1;
                setSelectedWeeks((prev) => {
                    prev[index] = true;
                    return [...prev];
                });
            });
        }
    }, [clazz, clazzEdit.week, propOpen]);

    async function handleOk() {
        emit(false);
    }

    async function moveClass() {
        const makeData = {
            class_grade: curriculum,
            class_name: clazzEdit.name,
            day_tick: clazzEdit.day_tick,
            end_tick: clazzEdit.end_tick,
            start_tick: clazzEdit.start_tick,
            original_day_tick: copyClazz.day_tick,
            original_end_tick: copyClazz.end_tick,
            original_start_tick: copyClazz.start_tick,
        } as ClassMutiMoveDTO;

        const getResp = await MoveMutiClassAPI(makeData);
        if (getResp?.output === "Success") {
            message.success("移动成功");
            emit(false);
            mergeRefresh(true);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    return (
        <Modal
            title="编辑课程"
            open={open}
            onOk={handleOk}
            onCancel={() => emit(false)}
            footer={
                <div className="flex gap-1 justify-end text-white">
                    <button onClick={() => emit(false)}
                            className="py-1.5 px-4 rounded-lg shadow bg-gray-500 hover:bg-gray-600 transition">
                        取消
                    </button>
                    <button
                        onClick={() => emit(false)}
                        className="py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition"
                    >
                        删除该课程
                    </button>
                    <button
                        onClick={moveClass}
                        className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"
                    >
                        移动该课程
                    </button>
                </div>
            }
        >
            <div className="grid grid-cols-2 gap-3">
                <div>
                    <label htmlFor="class_name" className="block text-xs font-medium text-gray-700">课程名字</label>
                    <input
                        type="text"
                        id="class_name"
                        placeholder="高等数学II"
                        value={clazzEdit.name}
                        onChange={(e) => setClazzEdit({...clazz, name: e.target.value})}
                        className="mt-1 w-full rounded-md border-gray-200 text-gray-800 bg-gray-100 shadow-sm sm:text-sm"
                        disabled={true}
                    />
                </div>
                <div>
                    <label htmlFor="class_week" className="block text-xs font-medium text-gray-700">周</label>
                    <select
                        name="class_week"
                        id="class_week"
                        onChange={(e) => {
                            console.log("执行了操作，获取结果", e.target.value)
                            setClazzEdit({...clazz, day_tick: Number(e.target.value)} as ClassEntity)
                            console.log("执行了操作，存储结果", clazzEdit)
                        }}
                        value={clazzEdit.day_tick ? clazzEdit.day_tick : 1}
                        className="mt-1 w-full rounded-md border-gray-200 sm:text-sm text-gray-800"
                    >
                        <option value={1}>周一</option>
                        <option value={2}>周二</option>
                        <option value={3}>周三</option>
                        <option value={4}>周四</option>
                        <option value={5}>周五</option>
                        <option value={6}>周六</option>
                        <option value={7}>周日</option>
                    </select>
                </div>
                <div>
                    <label htmlFor="start_time" className="block text-xs font-medium text-gray-700">开始节次</label>
                    <select
                        name="start_time"
                        id="start_time"
                        onChange={(e) => setClazzEdit({
                            ...clazzEdit,
                            start_tick: Number(e.target.value)
                        } as ClassEntity)}
                        value={clazzEdit.start_tick}
                        className="mt-1 w-full rounded-md border-gray-200 sm:text-sm text-gray-800"
                    >
                        {curriculumSelectTime.timetable?.map((item, index) => (
                            <option key={index} value={index} selected={index == 0}>{item.start_time}</option>
                        ))}
                    </select>
                </div>
                <div>
                    <label htmlFor="end_time" className="block text-xs font-medium text-gray-700">结束节次</label>
                    <select
                        name="end_time"
                        id="end_time"
                        onChange={(e) => setClazzEdit({...clazz, end_tick: Number(e.target.value)} as ClassEntity)}
                        value={clazzEdit.end_tick}
                        className="mt-1 w-full rounded-md border-gray-200 sm:text-sm text-gray-800"
                    >
                        {curriculumSelectTime.timetable?.map((item, index) => (
                            <option key={index} value={index+1}>{item.end_time}</option>
                        ))}
                    </select>
                </div>
                <div>
                    <label htmlFor="class_teacher" className="block text-xs font-medium text-gray-700">教师名字</label>
                    <div className="relative mt-1.5">
                        <input
                            type="text"
                            list="class_teacher"
                            id="class_teacher"
                            value={clazzEdit.teacher}
                            className="w-full rounded-md border-gray-200 pe-10 text-gray-900 bg-gray-100 sm:text-sm [&::-webkit-calendar-picker-indicator]:opacity-0"
                            placeholder="输入教师名字"
                            disabled={true}
                        />
                        <span className="absolute inset-y-0 end-0 flex w-8 items-center"><DashOutlined/></span>
                    </div>
                    <datalist id="class_teacher">
                        {localTeacher.current.map((item, index) => (
                            <option key={index} value={item}/>
                        ))}
                    </datalist>
                </div>
                <div>
                    <label htmlFor="class_location" className="block text-xs font-medium text-gray-700">上课位置</label>
                    <div className="relative mt-1.5">
                        <input
                            type="text"
                            list="class_location"
                            id="class_location"
                            value={clazzEdit.location}
                            className="w-full rounded-md border-gray-200 pe-10 text-gray-900 sm:text-sm bg-gray-100 [&::-webkit-calendar-picker-indicator]:opacity-0"
                            placeholder="敏学楼102"
                            disabled={true}
                        />
                        <span className="absolute inset-y-0 end-0 flex w-8 items-center"><DashOutlined/></span>
                    </div>
                    <datalist id="class_location">
                        {localTeacher.current.map((item, index) => (
                            <option key={index} value={item}/>
                        ))}
                    </datalist>
                </div>
                <fieldset className={"col-span-full"}>
                    <label htmlFor="weeks" className="block text-xs font-medium text-gray-700">上课周次</label>
                    <div className="grid gap-1 grid-cols-5 md:grid-cols-10 mt-1">
                        {selectedWeeks.map((isSelected, week) => (
                            <div
                                key={week}
                                className={`cursor-pointer transition flex items-center justify-center p-2 rounded-lg border ${
                                    isSelected ? 'bg-blue-500 text-white' : 'bg-gray-100 text-gray-900'
                                }`}
                            >
                                <strong className="text-sm font-thin select-none">{week + 1}</strong>
                            </div>
                        ))}
                    </div>
                </fieldset>
            </div>
        </Modal>
    )
}
